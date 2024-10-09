package br.edu.atitus.paradigma.cambio_service.controllers;

import br.edu.atitus.paradigma.cambio_service.entities.CambioEntity;
import br.edu.atitus.paradigma.cambio_service.repositories.CambioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cambio-service")
public class CambioController {

    private final CambioRepository cambioRepository;

    @Value("${spring.application.name}")
    private String ambiente;

    public CambioController(CambioRepository cambioRepository) {
        this.cambioRepository = cambioRepository;
    }

    @GetMapping("/{valor}/{origem}/{destino}")
    public CambioEntity getCambio(@PathVariable("valor") double valor,
                                  @PathVariable("origem") String origem,
                                  @PathVariable("destino") String destino) {

        Optional<CambioEntity> cambioOptional = cambioRepository.findByOrigemAndDestino(origem, destino);

        if (!cambioOptional.isPresent()) {
            throw new RuntimeException("Não foi possível encontrar a taxa de câmbio para " + origem + " para " + destino);
        }

        CambioEntity cambio = cambioOptional.get();


        double valorConvertido = valor * cambio.getFator();
        cambio.setValorConvertido(valorConvertido);
        cambio.setAmbiente(ambiente); // Setar o ambiente a partir das propriedades

        return cambio;
    }
}