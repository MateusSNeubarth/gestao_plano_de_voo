
package com.projarc.trabalhofinal.adapter.out.persistence.entity.plano;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.aeronave.AeronaveEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.malha.AeroportoEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaEntity;
import com.projarc.trabalhofinal.domain.model.plano.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "plano_de_voo")
@NoArgsConstructor
@AllArgsConstructor
public class PlanoDeVooEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false, name = "data_hora_partida")
    private LocalDateTime dataHoraPartida;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aeronave_prefixo")
    private AeronaveEntity aeronave;

    @ManyToOne(optional = false)
    @JoinColumn(name = "piloto_id")
    private PilotoEntity piloto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_origem")
    private AeroportoEntity origem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "codigo_destino")
    private AeroportoEntity destino;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "plano_id", nullable = false)
    @OrderColumn(name = "ordem")
    private List<TrechoEntity> trechos = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "plano_id", nullable = false)
    private List<ReservaEntity> reservas = new ArrayList<>();
}
