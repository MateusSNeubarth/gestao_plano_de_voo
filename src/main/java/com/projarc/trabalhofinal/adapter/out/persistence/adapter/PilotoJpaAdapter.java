package com.projarc.trabalhofinal.adapter.out.persistence.adapter;

import com.projarc.trabalhofinal.adapter.out.persistence.mapper.PilotoPersistenceMapper;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.PilotoJpaRepository;
import com.projarc.trabalhofinal.application.port.out.PilotoOutPort;
import com.projarc.trabalhofinal.domain.model.plano.Piloto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PilotoJpaAdapter implements PilotoOutPort {

    private final PilotoJpaRepository repository;

    @Override
    public List<Piloto> listar() {
        return repository.findAll().stream()
                .map(PilotoPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Piloto buscarPorId(Integer id) {
        return repository.findById(id)
                .map(PilotoPersistenceMapper::toDomain)
                .orElse(null);
    }

    @Override
    public Piloto criarPiloto(Piloto piloto) {
        var salvo = repository.save(PilotoPersistenceMapper.toEntity(piloto));
        return PilotoPersistenceMapper.toDomain(salvo);
    }
}