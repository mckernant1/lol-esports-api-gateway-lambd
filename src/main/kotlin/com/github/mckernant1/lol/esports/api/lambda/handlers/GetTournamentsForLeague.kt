package com.github.mckernant1.lol.esports.api.lambda.handlers

import com.amazonaws.services.dynamodbv2.document.ItemUtils
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.QueryRequest
import com.github.mckernant1.lol.esports.api.Tournament
import com.github.mckernant1.lol.esports.api.lambda.LEAGUES_TABLE_NAME
import com.github.mckernant1.lol.esports.api.lambda.TOURNAMENTS_TABLE_NAME
import com.github.mckernant1.lol.esports.api.lambda.ddb
import com.github.mckernant1.lol.esports.api.lambda.models.ErrorResponse
import com.github.mckernant1.lol.esports.api.lambda.models.Response
import com.github.mckernant1.lol.esports.api.lambda.models.SuccessResponse
import com.github.mckernant1.lol.esports.api.lambda.util.mapToObject

class GetTournamentsForLeague : AbstractPathVariableRequestHandler() {
    override val pathParamName: String = "leagueId"

    override fun execute(paramValue: String): Response {

        ddb.getItem(
            LEAGUES_TABLE_NAME,
            mapOf("leagueId" to AttributeValue(paramValue))
        ).item ?: return ErrorResponse(
            "leagueId $paramValue does not exist",
            "NoSuchLeagueException",
            404
        )

        val items = ddb.query(
            QueryRequest(TOURNAMENTS_TABLE_NAME)
                .withKeyConditionExpression("leagueId = :desiredLeague")
                .withExpressionAttributeValues(
                    mapOf(":desiredLeague" to AttributeValue(paramValue))
                )
        ).items.asSequence()
            .map { ItemUtils.toItem(it).asMap() }
            .map { mapToObject(it, Tournament::class) }
            .toList()

        return SuccessResponse(
            items,
            200
        )

    }
}
