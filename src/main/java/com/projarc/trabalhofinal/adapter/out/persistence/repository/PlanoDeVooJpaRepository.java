package com.projarc.trabalhofinal.adapter.out.persistence.repository;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PlanoDeVooEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanoDeVooJpaRepository extends JpaRepository<PlanoDeVooEntity, Integer> {
}
