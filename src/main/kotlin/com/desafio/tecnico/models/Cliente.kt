package com.desafio.tecnico.models

import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "clientes")
class Cliente {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @field:NotBlank(message = "O nome não pode ser em branco.")
    @field:Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres.")
    var nome: String = ""

    @field:NotBlank(message = "O CPF não pode ser em branco.")
    @field:Column(unique = true)
    var cpf: String = ""

    @field:Min(value = 18, message = "A idade deve ser maior que 18 anos.")
    var idade: Int = 0

    @field:Past(message = "A data de nascimento não pode ser no futuro.")
    var data_nascimento: LocalDate = LocalDate.MIN

    @field:Size(min = 2, max = 2, message = "A UF deve ter exatamente 2 caracteres.")
    var uf: String = ""

    @field:Positive(message = "A renda mensal deve ser positiva.")
    var renda_mensal: BigDecimal = BigDecimal.ZERO

    @field:Email(message = "E-mail inválido.")
    var email: String = ""

    @field:NotBlank(message = "O telefone do WhatsApp não pode ser em branco.")
    @field:Size(min = 11, max = 11, message = "O telefone do WhatsApp deve ter entre 10 e 15 caracteres.")
    var telefone_whatsapp: String = ""
}


