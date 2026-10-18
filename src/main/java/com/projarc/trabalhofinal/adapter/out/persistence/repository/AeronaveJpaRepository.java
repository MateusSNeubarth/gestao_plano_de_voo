package com.projarc.trabalhofinal.adapter.out.persistence.repository;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.AeronaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AeronaveJpaRepository extends JpaRepository<AeronaveEntity, String> {
}