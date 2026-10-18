package com.projarc.trabalhofinal.adapter.out.persistence.repository;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.PilotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotoJpaRepository extends JpaRepository<PilotoEntity, Integer> {
}