package com.projarc.trabalhofinal.adapter.out.persistence.adapter.plano;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.malha.AeroportoEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.malha.AeroviaEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PilotoEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PlanoDeVooEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.TrechoEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaAeroviaEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaEstacionamentoEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.mapper.PlanoPersistenceMapper;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.*;
import com.projarc.trabalhofinal.application.port.out.plano.PlanoDeVooPort;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlanoDeVooJpaAdapter implements PlanoDeVooPort {

    private final PlanoDeVooJpaRepository planoDeVooJpaRepository;
    private final PilotoJpaRepository pilotoJpaRepository;
    private final AeroportoJpaRepository aeroportoJpaRepository;
    private final AeroviaJpaRepository aeroviaJpaRepository;
    private final AeronaveJpaRepository aeronaveJpaRepository; // Esperar Bernardo implementear aerovia


    @Override
    public PlanoDeVoo criarPlano(PlanoDeVoo plano) {

        PlanoDeVooEntity planoEntity =
                PlanoPersistenceMapper.fromDomain(plano);

        // Busca o piloto existente
        PilotoEntity pilotoEntity = pilotoJpaRepository
                .findById(plano.getPiloto().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Piloto não encontrado"
                ));

        // Busca o aeroporto de origem
        AeroportoEntity origemEntity = aeroportoJpaRepository
                .findByCodigo(plano.getOrigem().getCodigo())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aeroporto de origem não encontrado"
                ));

        // Busca o aeroporto de destino
        AeroportoEntity destinoEntity = aeroportoJpaRepository
                .findByCodigo(plano.getDestino().getCodigo())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aeroporto de destino não encontrado"
                ));

        // Associa as entidades existentes ao plano
        planoEntity.setPiloto(pilotoEntity);
        planoEntity.setOrigem(origemEntity);
        planoEntity.setDestino(destinoEntity);

        // Resolve as aerovias dos trechos do percurso
        for (TrechoEntity trecho : planoEntity.getTrechos()) {

            String aeroviaId = trecho.getAerovia().getId();

            AeroviaEntity aeroviaEntity = aeroviaJpaRepository
                    .findById(aeroviaId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Aerovia não encontrada: " + aeroviaId
                    ));

            trecho.setAerovia(aeroviaEntity);
        }

        // Resolve as referências das reservas
        for (ReservaEntity reserva : planoEntity.getReservas()) {

            if (reserva instanceof ReservaAeroviaEntity reservaAerovia) {

                String aeroviaId = reservaAerovia.getAerovia().getId();

                AeroviaEntity aeroviaEntity = aeroviaJpaRepository
                        .findById(aeroviaId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Aerovia da reserva não encontrada: " + aeroviaId
                        ));

                reservaAerovia.setAerovia(aeroviaEntity);

            } else if (reserva instanceof ReservaEstacionamentoEntity reservaEstacionamento) {

                String codigoAeroporto =
                        reservaEstacionamento.getAeroporto().getCodigo();

                AeroportoEntity aeroportoEntity = aeroportoJpaRepository
                        .findByCodigo(codigoAeroporto)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Aeroporto da reserva não encontrado: "
                                        + codigoAeroporto
                        ));

                reservaEstacionamento.setAeroporto(aeroportoEntity);
            }
        }

        // Salva o plano
        PlanoDeVooEntity planoSalvo =
                planoDeVooJpaRepository.save(planoEntity);

        // Retorna o plano convertido para o domínio
        return PlanoPersistenceMapper.toDomain(planoSalvo);
    }

    @Override
    public PlanoDeVoo consultarPlano(Integer id) {
        return planoDeVooJpaRepository.findById(id)
                .map(PlanoPersistenceMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Plano não encontrado: " + id));
    }

    @Override
    public List<PlanoDeVoo> listarPlanosPorStatus(Status status) {
        return planoDeVooJpaRepository.findByStatus(status).stream()
                .map(PlanoPersistenceMapper::toDomain)
                .toList();
    }

}
