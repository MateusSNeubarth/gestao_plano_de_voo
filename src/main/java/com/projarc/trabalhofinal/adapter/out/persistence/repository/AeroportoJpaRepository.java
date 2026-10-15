package com.projarc.trabalhofinal.adapter.out.persistence.repository;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.malha.AeroportoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AeroportoJpaRepository extends JpaRepository<AeroportoEntity, String> {
    AeroportoEntity findByCodigo(String codigo);
}
