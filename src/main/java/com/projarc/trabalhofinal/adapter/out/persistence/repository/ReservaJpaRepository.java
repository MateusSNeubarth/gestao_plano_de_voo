package com.projarc.trabalhofinal.adapter.out.persistence.repository;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaAeroviaEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaEstacionamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ReservaJpaRepository extends JpaRepository<ReservaEntity, Integer> {

    @Query("select r from ReservaAeroviaEntity r where r.aerovia.id = :aeroviaId and r.slot in :slots")
    List<ReservaAeroviaEntity> findReservasDeAerovia(@Param("aeroviaId") String aeroviaId,
                                                     @Param("slots") Collection<LocalDateTime> slots);

    @Query("select r from ReservaEstacionamentoEntity r where r.aeroporto.codigo = :codigoAeroporto and r.slot in :slots")
    List<ReservaEstacionamentoEntity> findReservasDeEstacionamento(@Param("codigoAeroporto") String codigoAeroporto,
                                                                   @Param("slots") Collection<LocalDateTime> slots);
}
