package com.desafio.tecnico.repository

import com.desafio.tecnico.models.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteRepository : JpaRepository<Cliente, Long> {
    fun getByCpf(cpf: String): Cliente?
}