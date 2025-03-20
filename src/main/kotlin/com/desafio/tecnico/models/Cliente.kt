package com.desafio.tecnico.models

import java.math.BigDecimal
import java.time.LocalDate

class Cliente {
    var nome: String = ""
    var cpf: String = ""
    var idade: Int = 0
    var data_nascimento: LocalDate = LocalDate.MIN
    var uf: String = ""
    var renda_mensal: BigDecimal = BigDecimal.ZERO
    var email: String = ""
    var telefone_whatssap: String = ""

}