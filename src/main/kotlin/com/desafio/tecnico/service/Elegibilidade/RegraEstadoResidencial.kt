package com.desafio.tecnico.service.Elegibilidade

import com.desafio.tecnico.models.Cartao
import com.desafio.tecnico.models.Cliente
import com.desafio.tecnico.models.TipoCartao
import org.springframework.stereotype.Component

@Component
class RegraEstadoResidencial : RegraElegibilidade {
    override fun verificarElegibilidade(cliente: Cliente, cartao: Cartao): Boolean {
        return if (cliente.uf == "SP") {
            if (cliente.idade in 25..30) {
                // Clientes de SP entre 25 e 30: permitem todos os cartões.
                true
            } else {
                // Para os demais clientes de SP (que não estão entre 25 e 30), somente os cartões com cashback ou sem anuidade são permitidos.
                cartao.tipo_cartao == TipoCartao.CARTAO_COM_CASHBACK || cartao.tipo_cartao == TipoCartao.CARTAO_SEM_ANUIDADE
            }
        } else {
            // Se o cliente não reside em SP, essa regra não restringe.
            true
        }
    }
}