package com.desafio.tecnico.models

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "cartoes")

class Cartao {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    @field:Enumerated(EnumType.STRING)
    var tipo_cartao: TipoCartao = TipoCartao.CARTAO_SEM_ANUIDADE
    var valor_anuidade_mensal: BigDecimal = BigDecimal.ZERO
    var valor_limite_disponivel: BigDecimal = BigDecimal.ZERO
    var status: String = ""
    var renda_minima: BigDecimal = BigDecimal.ZERO

}