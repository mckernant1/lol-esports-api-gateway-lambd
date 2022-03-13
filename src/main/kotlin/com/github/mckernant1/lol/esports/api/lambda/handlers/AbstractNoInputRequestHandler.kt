package com.github.mckernant1.lol.esports.api.lambda.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.github.mckernant1.lol.esports.api.lambda.models.ErrorResponse
import com.github.mckernant1.lol.esports.api.lambda.models.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream
import java.io.OutputStream

abstract class AbstractNoInputRequestHandler : RequestStreamHandler {

    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val resp = try {
            val resp = execute()
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

    abstract fun execute(): Response

}
