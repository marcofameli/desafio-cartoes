package com.desafio.tecnico.models

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.math.BigDecimal
import com.fasterxml.jackson.core.JsonToken

class NumericOnlyDeserializer : JsonDeserializer<BigDecimal>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): BigDecimal {
        return when {
            parser.currentToken == JsonToken.VALUE_STRING -> {
                throw IllegalArgumentException("Use números (ex: 1000) em vez de Strings (ex: \"1000\").")
            }
            parser.currentToken.isNumeric -> parser.decimalValue
            else -> throw IllegalArgumentException("Tipo inválido. Envie um número (ex: 1000).")
        }
    }
}