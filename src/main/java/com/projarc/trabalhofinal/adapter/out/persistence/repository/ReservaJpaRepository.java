package com.projarc.trabalhofinal.adapter.out.persistence.repository;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.reserva.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaJpaRepository extends JpaRepository<ReservaEntity, Integer> {
}
