package com.github.mckernant1.lol.esports.api.lambda

import org.openapitools.client.api.DefaultApi

fun main(args: Array<String>) {
    val api = DefaultApi()

    val res = api.getLeagueByCodeWithHttpInfo("asdf")
}
