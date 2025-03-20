package com.desafio.tecnico.models

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "clientes")
class Cliente {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var nome: String = ""
    var cpf: String = ""
    var idade: Int = 0
    var data_nascimento: LocalDate = LocalDate.MIN
    var uf: String = ""
    var renda_mensal: BigDecimal = BigDecimal.ZERO
    var email: String = ""
    var telefone_whatsapp: String = ""

}