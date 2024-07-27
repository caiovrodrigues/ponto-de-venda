package com.caio.pdv.infra.integration.hospital.domain;

import jakarta.persistence.Column;

public record Medico(String nome, String crm, String password) {

}
