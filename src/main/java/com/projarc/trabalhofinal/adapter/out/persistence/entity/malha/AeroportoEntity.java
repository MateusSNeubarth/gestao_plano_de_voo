package com.projarc.trabalhofinal.adapter.out.persistence.entity.malha;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "aeroporto")
@NoArgsConstructor
@AllArgsConstructor
public class AeroportoEntity {

    @Id
    private String codigo;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, name = "qtd_vagas")
    private Integer qtdVagas;
}
