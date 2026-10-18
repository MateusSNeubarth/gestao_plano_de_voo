package com.projarc.trabalhofinal.adapter.out.persistence.adapter.plano;

import com.projarc.trabalhofinal.adapter.out.persistence.entity.aeronave.AeronaveEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.malha.AeroportoEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PilotoEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.PlanoDeVooEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.entity.plano.TrechoEntity;
import com.projarc.trabalhofinal.adapter.out.persistence.mapper.PlanoPersistenceMapper;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.AeronaveJpaRepository;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.AeroportoJpaRepository;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.AeroviaJpaRepository;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.PilotoJpaRepository;
import com.projarc.trabalhofinal.adapter.out.persistence.repository.PlanoDeVooJpaRepository;
import com.projarc.trabalhofinal.application.port.out.plano.PlanoDeVooPort;
import com.projarc.trabalhofinal.domain.exception.PlanoNaoEncontradoException;
import com.projarc.trabalhofinal.domain.model.plano.Percurso;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlanoDeVooJpaAdapter implements PlanoDeVooPort {

    private final PlanoDeVooJpaRepository planoDeVooJpaRepository;
    private final PilotoJpaRepository pilotoJpaRepository;
    private final AeroportoJpaRepository aeroportoJpaRepository;
    private final AeroviaJpaRepository aeroviaJpaRepository;
    private final AeronaveJpaRepository aeronaveJpaRepository;

    @Override
    @Transactional
    public PlanoDeVoo criarPlano(PlanoDeVoo plano) {
        PlanoDeVooEntity planoEntity = PlanoPersistenceMapper.fromDomain(plano);

        AeronaveEntity aeronaveEntity = aeronaveJpaRepository
                .findById(plano.getAeronave().getPrefixo())
                .orElseThrow(() -> new IllegalArgumentException("Aeronave não encontrada"));

        PilotoEntity pilotoEntity = pilotoJpaRepository
                .findById(plano.getPiloto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Piloto não encontrado"));

        AeroportoEntity origemEntity = aeroportoJpaRepository
                .findByCodigo(plano.getOrigem().getCodigo())
                .orElseThrow(() -> new IllegalArgumentException("Aeroporto de origem não encontrado"));

        AeroportoEntity destinoEntity = aeroportoJpaRepository
                .findByCodigo(plano.getDestino().getCodigo())
                .orElseThrow(() -> new IllegalArgumentException("Aeroporto de destino não encontrado"));

        planoEntity.setAeronave(aeronaveEntity);
        planoEntity.setPiloto(pilotoEntity);
        planoEntity.setOrigem(origemEntity);
        planoEntity.setDestino(destinoEntity);
        resolverAerovias(planoEntity.getTrechos());

        PlanoDeVooEntity planoSalvo = planoDeVooJpaRepository.save(planoEntity);
        return PlanoPersistenceMapper.toDomain(planoSalvo);
    }

    @Override
    @Transactional
    public List<PlanoDeVoo> listarPlanosPorStatus(Status status) {
        return planoDeVooJpaRepository.findByStatus(status).stream()
                .map(PlanoPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public PlanoDeVoo getPlanoDeVoo(Integer id) {
        return planoDeVooJpaRepository.findById(id).map(PlanoPersistenceMapper::toDomain).orElse(null);
    }

    @Override
    @Transactional
    public PlanoDeVoo updatePercurso(Integer id, Percurso percurso) {
        var plano = planoDeVooJpaRepository.findById(id).orElseThrow(() -> new PlanoNaoEncontradoException(id));
        var trechos = PlanoPersistenceMapper.trechosFromDomain(percurso.getTrechos());
        resolverAerovias(trechos);
        plano.getTrechos().clear();
        plano.getTrechos().addAll(trechos);
        return PlanoPersistenceMapper.toDomain(planoDeVooJpaRepository.saveAndFlush(plano));
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, Status status) {
        var plano = planoDeVooJpaRepository.findById(id).orElseThrow(() -> new PlanoNaoEncontradoException(id));
        plano.setStatus(status);
        planoDeVooJpaRepository.save(plano);
    }

    private void resolverAerovias(List<TrechoEntity> trechos) {
        for (TrechoEntity trecho : trechos) {
            String aeroviaId = trecho.getAerovia().getId();
            trecho.setAerovia(aeroviaJpaRepository
                    .findById(aeroviaId)
                    .orElseThrow(() -> new IllegalArgumentException("Aerovia não encontrada: " + aeroviaId)));
        }
    }
}
