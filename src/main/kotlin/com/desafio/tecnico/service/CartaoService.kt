package com.desafio.tecnico.service

import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.repository.CartaoRepository
import org.springframework.stereotype.Service

@Service
class CartaoService(private val cartaoRepository: CartaoRepository) {


    fun listarCartoesElegiveis(cliente: Cliente): List<Cartao> {
        return cartaoRepository.listarCartoesElegiveis(cliente.renda_mensal)
    }
}