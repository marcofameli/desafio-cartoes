package com.desafio.tecnico.dto

data class DetalheErroDTO(
    val app: String,
    val tipo_erro: String,
    val mensagem_interna: String
)