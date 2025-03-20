package com.desafio.tecnico.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate

data class ClienteResponse(
    val nome: String,
    val cpf: String,
    val idade: Int,
    val data_nascimento: LocalDate,
    val uf: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#0.00")
    val renda_mensal: BigDecimal,
    val email: String,
    val telefone_whatsapp: String
)