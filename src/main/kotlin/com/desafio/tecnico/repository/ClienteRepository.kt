package com.desafio.tecnico.repository

import com.desafio.tecnico.models.Cliente
import org.springframework.data.jpa.repository.JpaRepository

interface ClienteRepository : JpaRepository<Cliente, Long> {
    fun findByCpf(cpf: String): Cliente?
}