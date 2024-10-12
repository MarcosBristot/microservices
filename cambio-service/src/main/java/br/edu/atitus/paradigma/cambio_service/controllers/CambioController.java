package br.edu.atitus.paradigma.cambio_service.controllers;

import br.edu.atitus.paradigma.cambio_service.clients.CotacaoClient;
import br.edu.atitus.paradigma.cambio_service.entities.CambioEntity;
import br.edu.atitus.paradigma.cambio_service.repositories.CambioRepository;
import br.edu.atitus.paradigma.cambio_service.response.CotacaoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

	private final CambioRepository cambioRepository;
	private final CotacaoClient cotacaoClient;

	public CambioController(CambioRepository cambioRepository, CotacaoClient cotacaoClient) {
		super();
		this.cambioRepository = cambioRepository;
		this.cotacaoClient = cotacaoClient;
	}

	@Value("${server.port}")
	private int porta;

	@GetMapping("/{valor}/{origem}/{destino}")
	public ResponseEntity<CambioEntity> getCambio(
			@PathVariable double valor,
			@PathVariable String origem,
			@PathVariable String destino) throws Exception {

		CambioEntity cambio = cambioRepository.findByOrigemAndDestino(origem, destino)
				.orElseThrow(() -> new Exception("Câmbio não encontrado para esta origem e destino"));

		String dataCotacao = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

		CotacaoResponse cotacaoResponse = cotacaoClient.getCotacaoMoedaDia(origem, dataCotacao);
		if (cotacaoResponse != null && !cotacaoResponse.getValue().isEmpty()) {
			double cotacaoVenda = cotacaoResponse.getValue().get(0).getCotacaoVenda();
		}

		cambio.setValorConvertido(valor * cambio.getFator());
		cambio.setAmbiente("Cambio-Service run in port: " + porta);

		return ResponseEntity.ok(cambio);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		String cleanMessage = e.getMessage().replaceAll("[\\r\\n]", " ");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cleanMessage);
	}
}
