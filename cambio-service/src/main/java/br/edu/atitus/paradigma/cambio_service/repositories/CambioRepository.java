package br.edu.atitus.paradigma.cambio_service.repositories;

import br.edu.atitus.paradigma.cambio_service.entities.CambioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CambioRepository extends JpaRepository<CambioEntity, Integer> {

    // Método para encontrar uma entidade Cambio pela origem e destino
    Optional<CambioEntity> findByOrigemAndDestino(String origem, String destino);
}