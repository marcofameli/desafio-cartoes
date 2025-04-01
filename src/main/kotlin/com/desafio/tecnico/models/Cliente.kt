package com.desafio.tecnico.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import jakarta.persistence.*
import jakarta.validation.constraints.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "clientes")
data class Cliente (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @field:NotBlank(message = "O nome não pode ser em branco.")
    @field:Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres.")
    @field:Pattern(regexp = "^[a-zA-Z\\sáéíóúãõâêîôûçÁÉÍÓÚÃÕÂÊÎÔÛÇ]+$", message = "O nome deve conter apenas letras.")
    var nome: String = "",

    @field:NotBlank(message = "O CPF não pode ser em branco.")
    @field:Pattern(regexp = "^[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}$", message = "CPF deve ser no formato XXX.XXX.XXX-XX")
    @field:Column(unique = true)
    var cpf: String = "",

    @field:Positive(message = "A idade deve ser maior que 0.")
    @field:Min(value = 18, message = "A idade deve ser maior que 18 anos.")
    @field:Max(value = 120, message = "A idade não pode ser maior que 120 anos.")
    var idade: Int = 0,

    @field:Past(message = "A data de nascimento não pode ser no futuro.")
    var data_nascimento: LocalDate = LocalDate.MIN,

    @field:Size(min = 2, max = 2, message = "A UF deve ter exatamente 2 caracteres.")
    @field:Pattern(regexp = "^[A-Z]{2}$", message = "A UF deve conter exatamente 2 letras maiusculas.")
    var uf: String = "",

    @field:Positive(message = "A renda mensal deve ser positiva.")
    @JsonDeserialize(using = NumericOnlyDeserializer::class)
    var renda_mensal: BigDecimal = BigDecimal.ZERO,

    @field:Email(message = "E-mail inválido.")
    var email: String = "",

    @field:NotBlank(message = "O telefone do WhatsApp não pode ser em branco.")
    @field:Size(min = 11, max = 11, message = "O telefone do WhatsApp deve ter entre 10 e 15 caracteres.")
    var telefone_whatsapp: String = ""
)



