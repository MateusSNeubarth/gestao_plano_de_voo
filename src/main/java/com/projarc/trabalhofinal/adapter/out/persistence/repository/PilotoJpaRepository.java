package com.projarc.trabalhofinal.adapter.out.persistence.repository;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PilotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotoJpaRepository extends JpaRepository<PilotoEntity, Integer> {
}
