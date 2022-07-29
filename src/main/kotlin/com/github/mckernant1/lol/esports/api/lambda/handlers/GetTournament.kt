package com.github.mckernant1.lol.esports.api.lambda.handlers

import com.amazonaws.services.dynamodbv2.document.ItemUtils
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.QueryRequest
import com.github.mckernant1.lol.esports.api.Tournament
import com.github.mckernant1.lol.esports.api.lambda.TOURNAMENTS_TABLE_NAME
import com.github.mckernant1.lol.esports.api.lambda.TOURNAMENT_INDEX
import com.github.mckernant1.lol.esports.api.lambda.ddb
import com.github.mckernant1.lol.esports.api.lambda.models.ErrorResponse
import com.github.mckernant1.lol.esports.api.lambda.models.Response
import com.github.mckernant1.lol.esports.api.lambda.models.SuccessResponse
import com.github.mckernant1.lol.esports.api.lambda.util.mapToObject

class GetTournament : AbstractPathVariableRequestHandler() {
    override val pathParamName: String = "tournamentId"

    override fun execute(paramValue: String): Response {
        val item = ddb.query(
            QueryRequest(TOURNAMENTS_TABLE_NAME)
                .withIndexName(TOURNAMENT_INDEX)
                .withKeyConditionExpression("tournamentId = :desiredTourney")
                .withExpressionAttributeValues(
                    mapOf(":desiredTourney" to AttributeValue(paramValue))
                )
        ).items.asSequence()
            .map { ItemUtils.toItem(it).asMap() }
            .map { mapToObject(it, Tournament::class) }
            .firstOrNull()
            ?: return ErrorResponse(
                "The tournament with id $paramValue does not exist",
                "NoSuchTournamentException",
                404
            )



        return SuccessResponse(
            item,
            200
        )

    }
}
