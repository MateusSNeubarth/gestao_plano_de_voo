package com.projarc.trabalhofinal.adapter.out.persistence.repository;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.malha.AeroportoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AeroportoJpaRepository extends JpaRepository<AeroportoEntity, String> {
    Optional<AeroportoEntity> findByCodigo(String codigo);
}
