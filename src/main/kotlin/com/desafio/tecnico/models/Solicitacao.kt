package com.desafio.tecnico.models

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "solicitacao")
data class Solicitacao (

    @field:Id
    @field:Column(name = "numero_solicitacao", unique = true, nullable = false, updatable = false)
    var numeroSolicitacao: UUID = UUID.randomUUID(),

    @field:Column(name = "data_solicitacao", nullable = false, updatable = false)
    var dataSolicitacao: LocalDateTime = LocalDateTime.now(),

    @field:ManyToOne
    @field:JoinColumn(name = "cliente_id", referencedColumnName = "id")
     var cliente: Cliente,

    @field:ManyToMany
    @field:JoinTable(
        name = "solicitacao_cartao",
        joinColumns = [JoinColumn(name = "solicitacao_id")],
        inverseJoinColumns = [JoinColumn(name = "cartao_id")]
    )
    var cartoes: List<Cartao>? = null

) {
    constructor() : this(UUID.randomUUID(), LocalDateTime.now(), Cliente(), null)
}


