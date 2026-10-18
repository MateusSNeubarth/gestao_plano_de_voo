package com.projarc.trabalhofinal.adapter.out.persistence.repository;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PlanoDeVooEntity;
import com.projarc.trabalhofinal.domain.model.plano.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanoDeVooJpaRepository extends JpaRepository<PlanoDeVooEntity, Integer> {
    List<PlanoDeVooEntity> findByStatus(Status status);
}
