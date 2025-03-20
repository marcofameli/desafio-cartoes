package com.desafio.tecnico.dto

import com.desafio.tecnico.models.TipoCartao
import java.math.BigDecimal

class CartaoOfertadoResponse(
    val tipoCartao: TipoCartao,
    val valor_anuidade_mensal: BigDecimal,
    val valor_limite_disponivel: BigDecimal,
    val status: String

)