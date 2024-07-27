package com.caio.pdv.infra.integration.hospital;

import com.caio.pdv.infra.integration.hospital.domain.Medico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class IntegracaoHospitalService {

    private final WebClient webClient;

    public Medico getMedicosFromHospital() {
        return webClient.get().uri("http://localhost:8080/api/medico").retrieve().bodyToMono(Medico.class).block();
    }

}
