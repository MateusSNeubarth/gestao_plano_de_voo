package com.projarc.trabalhofinal.application.usecase.cadastro;

import com.projarc.trabalhofinal.application.port.in.PilotoInPort;
import com.projarc.trabalhofinal.application.port.out.PilotoOutPort;
import com.projarc.trabalhofinal.domain.model.plano.Piloto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PilotoUseCase implements PilotoInPort {

    private final PilotoOutPort pilotoOutPort;

    @Override
    public List<Piloto> listar() {
        return pilotoOutPort.listar();
    }

    @Override
    public Piloto buscarPorId(Integer id) {
        return pilotoOutPort.buscarPorId(id);
    }

    @Override
    public Piloto criarPiloto(Piloto piloto) {
        return pilotoOutPort.criarPiloto(piloto);
    }
}