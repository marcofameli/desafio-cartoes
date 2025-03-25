package com.desafio.tecnico.service

import com.desafio.tecnico.exceptions.RegraNegocioException
import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.repository.CartaoRepository
import com.desafio.tecnico.service.Elegibilidade.RegraElegibilidade
import org.springframework.stereotype.Service

@Service
class CartaoService(
    private val cartaoRepository: CartaoRepository,
    private val regras: List<RegraElegibilidade>
) {
    fun listarCartoesElegiveis(cliente: Cliente): List<Cartao> {

        val cartoesPorRenda = cartaoRepository.listarCartoesElegiveis(cliente.renda_mensal)

        val cartoesElegiveis = cartoesPorRenda.filter { cartao ->
            regras.all { regra -> regra.verificarElegibilidade(cliente, cartao) }
        }

        return cartoesElegiveis
    }
}