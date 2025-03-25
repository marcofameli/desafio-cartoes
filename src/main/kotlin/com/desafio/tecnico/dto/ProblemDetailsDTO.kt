package com.desafio.tecnico.dto

data class ProblemDetailsDTO(
    val codigo: String,
    val mensagem: String,
    val detalhe_erro: DetalheErroDTO
)