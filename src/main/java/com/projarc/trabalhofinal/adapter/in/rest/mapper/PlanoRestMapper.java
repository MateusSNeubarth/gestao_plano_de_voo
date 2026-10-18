package com.projarc.trabalhofinal.adapter.in.rest.mapper;

import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.request.PlanoDeVooRequest;
import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.request.TrechoRequest;
import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response.PlanoDeVooResponse;
import com.projarc.trabalhofinal.adapter.in.rest.dto.plano.response.TrechoResponse;
import com.projarc.trabalhofinal.domain.model.aeronave.Aeronave;
import com.projarc.trabalhofinal.domain.model.malha.Aeroporto;
import com.projarc.trabalhofinal.domain.model.malha.Aerovia;
import com.projarc.trabalhofinal.domain.model.plano.Piloto;
import com.projarc.trabalhofinal.domain.model.plano.PlanoDeVoo;
import com.projarc.trabalhofinal.domain.model.plano.Trecho;

import java.util.List;
import java.util.stream.IntStream;

public final class PlanoRestMapper {

    private PlanoRestMapper() {}

    public static PlanoDeVoo toDomain(PlanoDeVooRequest request, Aeronave aeronave) {
        return PlanoDeVoo.builder()
                .dataHoraPartida(request.dataHoraPartida())
                .aeronave(aeronave)
                .piloto(Piloto.builder().id(request.pilotoId()).build())
                .origem(Aeroporto.builder().codigo(request.codOrigem()).build())
                .destino(Aeroporto.builder().codigo(request.codDestino()).build())
                .build();
    }

    public static List<Trecho> toDomain(List<TrechoRequest> requests) {
        if (requests == null) {
            return null;
        }
        return requests.stream().map(PlanoRestMapper::toDomain).toList();
    }

    public static Trecho toDomain(TrechoRequest request) {
        if (request == null) {
            return null;
        }
        return Trecho.builder()
                .aerovia(request.aeroviaId() == null ? null : Aerovia.builder().id(request.aeroviaId()).build())
                .altitude(request.altitude())
                .horarioEntradaNaAerovia(request.horarioEntradaNaAerovia())
                .acaoNaChegada(request.acaoNaChegada())
                .numeroVagaPretendida(request.numeroVagaPretendida())
                .tempoPermanenciaMinutos(request.tempoPermanenciaMinutos())
                .build();
    }

    public static PlanoDeVooResponse fromDomain(PlanoDeVoo plano) {
        int velocidade = plano.getAeronave().getVelocidadeDeCruzeiro();
        var trechos = plano.getPercurso().getTrechos();
        return PlanoDeVooResponse.builder()
                .id(plano.getId())
                .status(plano.getStatus().name())
                .dataHoraPartida(plano.getDataHoraPartida())
                .pilotoId(plano.getPiloto().getId())
                .pilotoNome(plano.getPiloto().getNome())
                .aeronavePrefixo(plano.getAeronave().getPrefixo())
                .codOrigem(plano.getOrigem().getCodigo())
                .codDestino(plano.getDestino().getCodigo())
                .trechos(IntStream.range(0, trechos.size())
                        .mapToObj(i -> fromDomain(i + 1, trechos.get(i), velocidade))
                        .toList())
                .build();
    }

    public static TrechoResponse fromDomain(int ordem, Trecho trecho, int velocidade) {
        return TrechoResponse.builder()
                .ordem(ordem)
                .aeroviaId(trecho.getAerovia().getId())
                .altitude(trecho.getAltitude())
                .horarioEntradaNaAerovia(trecho.getHorarioEntradaNaAerovia())
                .chegada(trecho.chegada(velocidade))
                .acaoNaChegada(trecho.getAcaoNaChegada().name())
                .numeroVagaPretendida(trecho.getNumeroVagaPretendida())
                .tempoPermanenciaMinutos(trecho.getTempoPermanenciaMinutos())
                .fimDaPermanencia(trecho.fimDaPermanencia(velocidade).orElse(null))
                .build();
    }
}
