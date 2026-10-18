package com.projarc.trabalhofinal.adapter.out.persistence.entity.plano;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.malha.AeroviaEntity;
import com.projarc.trabalhofinal.domain.model.plano.AcaoNaChegada;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "trecho")
@NoArgsConstructor
@AllArgsConstructor
public class TrechoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aerovia_id")
    private AeroviaEntity aerovia;

    @Column(nullable = false)
    private Integer altitude;

    @Column(nullable = false, name = "horario_entrada_na_aerovia")
    private LocalDateTime horarioEntradaNaAerovia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "acao_na_chegada")
    private AcaoNaChegada acaoNaChegada;

    @Column(name = "numero_vaga_pretendida")
    private Integer numeroVagaPretendida;

    @Column(name = "tempo_permanencia_minutos")
    private Integer tempoPermanenciaMinutos;
}
