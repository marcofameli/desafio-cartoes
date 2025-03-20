package com.desafio.tecnico.repository

import com.desafio.tecnico.models.Cartao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface CartaoRepository : JpaRepository<Cartao, Long> {

  @Query("SELECT * FROM cartoes WHERE :rendaMensal >= renda_minima", nativeQuery = true)
    fun listarCartoesElegiveis(rendaMensal: BigDecimal): List<Cartao>
}

