package com.github.mckernant1.lol.esports.api.lambda.models

import com.github.mckernant1.lol.esports.api.lambda.gson

class ErrorResponse(
    val body: String,
    val statusCode: Int,
    val headers: Map<String, String> = mapOf("Content-Type" to "application/json"),
) : Response {

    constructor(
        message: String,
        error: String,
        statusCode: Int,
        headers: Map<String, String> = mapOf("Content-Type" to "application/json")
    ) : this(
        gson.toJson(
            mapOf(
                "message" to message,
                "error" to error
            )
        ),
        statusCode,
        headers
    )

    override fun toJson(): String {
        return gson.toJson(this)
    }

}
