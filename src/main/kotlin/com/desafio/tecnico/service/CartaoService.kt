package com.desafio.tecnico.service


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
        val cartoesElegiveis = cartaoRepository.listarCartoesElegiveis(cliente.renda_mensal)
        return cartoesElegiveis.filter { cartao ->
            regras.all { regra -> regra.verificarElegibilidade(cliente,cartao) }
        }
    }

}