package com.desafio.tecnico.service.Elegibilidade

import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente

interface RegraElegibilidade {
    fun verificarElegibilidade (cliente: Cliente, cartao: Cartao): Boolean
}

