package com.desafio.tecnico.dto

import java.time.LocalDateTime
import java.util.*

data class SolitacaoResponseDTO(
    val numero_solicitacao: UUID,
    val data_solicitacao: LocalDateTime,
    val cliente: ClienteResponse,
    val cartoes_ofertados: List<CartaoOfertadoResponseDTO>
)

