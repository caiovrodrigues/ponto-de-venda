package com.caio.pdv;

import com.caio.pdv.infra.integration.hospital.IntegracaoHospitalService;
import com.caio.pdv.infra.integration.hospital.domain.Medico;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@SpringBootApplication
public class PdvApplication {

	private final IntegracaoHospitalService hospitalService;

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(PdvApplication.class);

		var activeProfile = System.getenv("SPRING_ACTIVE_PROFILE");

		springApplication.setAdditionalProfiles(Objects.requireNonNullElse(activeProfile, "local"));

		springApplication.run();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/integracao-medico", produces = "application/json")
	public ResponseEntity<Medico> responseEntity(){
		Medico medicosFromHospital = hospitalService.getMedicosFromHospital();
		System.out.println(medicosFromHospital);
		return ResponseEntity.ok(medicosFromHospital);
	}

}
