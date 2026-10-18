package com.projarc.trabalhofinal.adapter.out.persistence.adapter.plano;

import com.projarc.trabalhofinal.adapter.out.persistence.mapper.PlanoPersistenceMapper;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.PlanoDeVooJpaRepository;
import com.projarc.trabalhofinal.application.port.out.plano.PlanoDeVooPort;
import com.projarc.trabalhofinal.domain.exception.PlanoNaoEncontradoException;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PlanoDeVooJpaAdapter implements PlanoDeVooPort {

    private final PlanoDeVooJpaRepository repository;

    @Override
    @Transactional
    public PlanoDeVoo getPlanoDeVoo(Integer id) {
        return repository.findById(id).map(PlanoPersistenceMapper::toDomain).orElse(null);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, Status status) {
        var plano = repository.findById(id).orElseThrow(() -> new PlanoNaoEncontradoException(id));
        plano.setStatus(status);
        repository.save(plano);
    }
}
