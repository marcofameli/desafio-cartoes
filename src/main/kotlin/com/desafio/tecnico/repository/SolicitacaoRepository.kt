package com.desafio.tecnico.repository

import com.desafio.tecnico.models.Solicitacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SolicitacaoRepository : JpaRepository<Solicitacao, UUID> {
}