package com.desafio.tecnico.controller

import com.desafio.tecnico.dto.ClienteRequestWrapper
import com.desafio.tecnico.dto.SolicitacaoResponseDTO
import com.desafio.tecnico.service.SolicitacaoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Validated
@RestController
@RequestMapping("/cartoes")
class CartaoController(
    private val solicitacaoService: SolicitacaoService,
) {
    @PostMapping
    @Operation(
        summary = "Verificar cartões elegíveis",
        description = "Realiza verificação de cartões para um cliente"
    )


    fun verificarCartoesElegiveis(@Valid @RequestBody wrapper: ClienteRequestWrapper): ResponseEntity<SolicitacaoResponseDTO> {

        val numeroSolicitacao = UUID.randomUUID()

        val response = solicitacaoService.processarSolicitacao(wrapper.cliente, numeroSolicitacao)

        return response
    }
}