package com.github.mckernant1.lol.esports.api.lambda.handlers

import com.amazonaws.services.dynamodbv2.document.ItemUtils
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.github.mckernant1.lol.esports.api.League
import com.github.mckernant1.lol.esports.api.lambda.LEAGUES_TABLE_NAME
import com.github.mckernant1.lol.esports.api.lambda.ddb
import com.github.mckernant1.lol.esports.api.lambda.models.ErrorResponse
import com.github.mckernant1.lol.esports.api.lambda.models.Response
import com.github.mckernant1.lol.esports.api.lambda.models.SuccessResponse
import com.github.mckernant1.lol.esports.api.lambda.util.mapToObject

class GetLeague : AbstractPathVariableRequestHandler() {

    override val pathParamName: String = "leagueId"

    override fun execute(paramValue: String): Response {
        val item = ddb.getItem(
            LEAGUES_TABLE_NAME,
            mapOf("leagueId" to AttributeValue(paramValue))
        ).item ?: run {
            logger.warn("Sending error response")
            return ErrorResponse(
                "leagueId $paramValue does not exist",
                "NoSuchLeagueException",
                400
            )
        }

        val mappedItem = mapToObject(ItemUtils.toItem(item).asMap(), League::class)

        return SuccessResponse(
            mappedItem,
            200,
        )
    }
}
