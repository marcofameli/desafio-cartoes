package com.desafio.tecnico.dto

import java.math.BigDecimal
import java.time.LocalDate

data class ClienteResponse(
    val nome: String,
    val cpf: String,
    val idade: Int,
    val data_nascimento: LocalDate,
    val uf: String,
    val renda_mensal: BigDecimal,
    val email: String,
    val telefone_whatsapp: String
)