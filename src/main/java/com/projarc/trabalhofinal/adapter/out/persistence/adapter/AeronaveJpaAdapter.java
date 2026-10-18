package com.projarc.trabalhofinal.adapter.out.persistence.adapter;

import com.projarc.trabalhofinal.adapter.out.persistence.mapper.AeronavePersistenceMapper;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.AeronaveJpaRepository;
import com.projarc.trabalhofinal.application.port.out.AeronavesOutPort;
import com.projarc.trabalhofinal.domain.model.aeronave.Aeronave;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AeronaveJpaAdapter implements AeronavesOutPort {

    private final AeronaveJpaRepository repository;

    @Override
    public List<Aeronave> listar() {
        return repository.findAll(Sort.by("prefixo")).stream()
                .map(AeronavePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Aeronave buscarPorPrefixo(String prefixo) {
        return repository.findById(prefixo)
                .map(AeronavePersistenceMapper::toDomain)
                .orElse(null);
    }

    @Override
    public boolean existe(String prefixo) {
        return repository.existsById(prefixo);
    }

    @Override
    public Aeronave criarAeronave(Aeronave aeronave) {
        var salva = repository.save(AeronavePersistenceMapper.toEntity(aeronave));
        return AeronavePersistenceMapper.toDomain(salva);
    }
}