package com.github.mckernant1.lol.esports.api.lambda.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.github.mckernant1.lol.esports.api.lambda.gson
import com.github.mckernant1.lol.esports.api.lambda.models.ErrorResponse
import com.github.mckernant1.lol.esports.api.lambda.models.Response
import com.google.gson.JsonObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStream
import java.io.OutputStream

abstract class AbstractPathVariableRequestHandler : RequestStreamHandler {

    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)

    protected abstract val pathParamName: String

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val resp = try {
            val inputText = input.bufferedReader().use(BufferedReader::readText)
            val inputJson = gson.fromJson(inputText, JsonObject::class.java)

            val paramValue = inputJson["pathParameters"]!!
                .asJsonObject!!
                .get(pathParamName)!!
                .asString

            val resp = execute(paramValue)
            resp
        } catch (e: Exception) {
            logger.error("Hit Exception", e)
            ErrorResponse(
                e.message ?: "null",
                e.javaClass.simpleName,
                500
            )
        }

        output.bufferedWriter().use {
            it.write(resp.toJson())
        }
    }

    abstract fun execute(paramValue: String): Response


}
