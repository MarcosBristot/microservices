package br.edu.atitus.paradigma.cambio_service.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cambio")
public class CambioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 3)
    private String origem;

    @Column(nullable = false, length = 3)
    private String destino;

    @Column(nullable = false)
    private double fator;

    @Transient
    private String ambiente; // Não será persistido no banco de dados

    @Transient
    private double valorConvertido; // Não será persistido no banco de dados

    public CambioEntity() {
    }

    public CambioEntity(String origem, String destino, double fator, String ambiente, double valorConvertido) {
        this.origem = origem;
        this.destino = destino;
        this.fator = fator;
        this.ambiente = ambiente;
        this.valorConvertido = valorConvertido;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getFator() {
        return fator;
    }

    public void setFator(double fator) {
        this.fator = fator;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public double getValorConvertido() {
        return valorConvertido;
    }

    public void setValorConvertido(double valorConvertido) {
        this.valorConvertido = valorConvertido;
    }
}