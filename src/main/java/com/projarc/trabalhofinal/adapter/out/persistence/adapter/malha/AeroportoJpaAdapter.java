package com.projarc.trabalhofinal.adapter.out.persistence.adapter.malha;

import com.projarc.trabalhofinal.adapter.out.persistence.mapper.MalhaPersistenceMapper;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.AeroportoJpaRepository;
import com.projarc.trabalhofinal.application.port.out.malha.AeroportoPort;
import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AeroportoJpaAdapter implements AeroportoPort {

    private final AeroportoJpaRepository aeroportoJpaRepository;

    @Override
    public List<Aeroporto> getAeroportos() {
        return aeroportoJpaRepository.findAll().stream()
                .map(MalhaPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Aeroporto getAeroporto(String codigo) {
        return aeroportoJpaRepository.findByCodigo(codigo).map(MalhaPersistenceMapper::toDomain).orElse(null);
    }

    @Override
    public void createAeroporto(Aeroporto aeroporto) {
        aeroportoJpaRepository.save(MalhaPersistenceMapper.fromDomain(aeroporto));
    }
}
