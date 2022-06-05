package com.github.mckernant1.lol.esports.api.lambda.handlers

import com.amazonaws.services.dynamodbv2.document.ItemUtils
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.github.mckernant1.lol.esports.api.Team
import com.github.mckernant1.lol.esports.api.lambda.TEAMS_TABLE_NAME
import com.github.mckernant1.lol.esports.api.lambda.ddb
import com.github.mckernant1.lol.esports.api.lambda.models.ErrorResponse
import com.github.mckernant1.lol.esports.api.lambda.models.Response
import com.github.mckernant1.lol.esports.api.lambda.models.SuccessResponse
import com.github.mckernant1.lol.esports.api.lambda.util.mapToObject

class GetTeam : AbstractPathVariableRequestHandler() {
    override val pathParamName: String = "teamId"

    override fun execute(paramValue: String): Response {
        val item = ddb.getItem(
            TEAMS_TABLE_NAME,
            mapOf(
                "teamId" to AttributeValue(paramValue)
            )
        ).item ?: run {
            logger.warn("Sending error response")
            return ErrorResponse(
                "teamId $paramValue does not exist",
                "NoSuchTeamException",
                404
            )
        }

        val mappedItem = mapToObject(ItemUtils.toItem(item).asMap(), Team::class)

        return SuccessResponse(
            mappedItem,
            200,
        )
    }
}
