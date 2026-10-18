package com.projarc.trabalhofinal.adapter.out.persistence.adapter.reserva;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.mapper.ReservaPersistenceMapper;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.AeroportoJpaRepository;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.AeroviaJpaRepository;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.PlanoDeVooJpaRepository;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.ReservaJpaRepository;
import com.projarc.trabalhofinal.application.port.out.reserva.ReservaPort;
import com.projarc.trabalhofinal.domain.model.reserva.Reserva;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaAerovia;
import com.projarc.trabalhofinal.domain.model.reserva.ReservaEstacionamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservaJpaAdapter implements ReservaPort {

    private final ReservaJpaRepository reservaJpaRepository;
    private final PlanoDeVooJpaRepository planoDeVooJpaRepository;
    private final AeroviaJpaRepository aeroviaJpaRepository;
    private final AeroportoJpaRepository aeroportoJpaRepository;

    @Override
    public List<ReservaAerovia> getReservasDeAerovia(String aeroviaId, Collection<LocalDateTime> slots) {
        return reservaJpaRepository.findReservasDeAerovia(aeroviaId, slots).stream()
                .map(ReservaPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<ReservaEstacionamento> getReservasDeEstacionamento(String codigoAeroporto, Collection<LocalDateTime> slots) {
        return reservaJpaRepository.findReservasDeEstacionamento(codigoAeroporto, slots).stream()
                .map(ReservaPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public List<Reserva> createReservas(Integer planoId, List<Reserva> reservas) {
        var plano = planoDeVooJpaRepository.findById(planoId)
                .orElseThrow(() -> new IllegalArgumentException("Plano de voo não encontrado: " + planoId));
        var entities = reservas.stream().map(this::fromDomain).toList();
        plano.getReservas().addAll(entities);
        planoDeVooJpaRepository.flush();
        return entities.stream()
                .map(ReservaPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteReservas(Integer planoId) {
        planoDeVooJpaRepository.findById(planoId).ifPresent(plano -> {
            plano.getReservas().clear();
            planoDeVooJpaRepository.flush();
        });
    }

    private ReservaEntity fromDomain(Reserva reserva) {
        return switch (reserva) {
            case ReservaAerovia reservaAerovia -> ReservaPersistenceMapper.fromDomain(reservaAerovia,
                    aeroviaJpaRepository.getReferenceById(reservaAerovia.getAerovia().getId()));
            case ReservaEstacionamento reservaEstacionamento -> ReservaPersistenceMapper.fromDomain(reservaEstacionamento,
                    aeroportoJpaRepository.getReferenceById(reservaEstacionamento.getAeroporto().getCodigo()));
            default -> throw new IllegalArgumentException("Tipo de reserva desconhecido: " + reserva.getClass().getSimpleName());
        };
    }
}
