package com.github.mckernant1.lol.esports.api.lambda.models

import com.github.mckernant1.lol.esports.api.lambda.gson

data class SuccessResponse(
    val body: String,
    val statusCode: Int = 200,
    val headers: Map<String, String> = mapOf("Content-Type" to "application/json"),
) : Response {

    constructor(
        body: Any,
        statusCode: Int,
        headers: Map<String, String> =  mapOf("Content-Type" to "application/json")
    ) : this(
        gson.toJson(body),
        statusCode,
        headers
    )

    override fun toJson(): String {
        return gson.toJson(this)
    }
}
