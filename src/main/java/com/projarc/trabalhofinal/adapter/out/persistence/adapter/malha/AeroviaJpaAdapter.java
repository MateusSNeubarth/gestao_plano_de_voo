package com.projarc.trabalhofinal.adapter.out.persistence.adapter.malha;

import com.projarc.trabalhofinal.adapter.out.persistence.mapper.MalhaPersistenceMapper;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.AeroviaJpaRepository;
import com.projarc.trabalhofinal.application.port.out.malha.AeroviaPort;
import com.projarc.trabalhofinal.domain.model.malha.Aerovia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AeroviaJpaAdapter implements AeroviaPort {

    private final AeroviaJpaRepository repository;

    @Override
    public List<Aerovia> getAerovias() {
        return repository.findAll().stream()
                .map(MalhaPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Aerovia getAerovia(String id) {
        return repository.findById(id).map(MalhaPersistenceMapper::toDomain).orElse(null);
    }

    @Override
    public void createAerovia(Aerovia aerovia) {
        repository.save(MalhaPersistenceMapper.fromDomain(aerovia));
    }
}
