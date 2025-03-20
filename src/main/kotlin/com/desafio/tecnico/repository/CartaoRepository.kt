package com.desafio.tecnico.repository

import com.desafio.tecnico.models.Cartao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartaoRepository : JpaRepository<Cartao, Long> {
}