package com.github.mckernant1.lol.esports.api.lambda.handlers

import com.amazonaws.services.dynamodbv2.document.ItemUtils
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import com.github.mckernant1.lol.esports.api.League
import com.github.mckernant1.lol.esports.api.lambda.LEAGUES_TABLE_NAME
import com.github.mckernant1.lol.esports.api.lambda.ddb
import com.github.mckernant1.lol.esports.api.lambda.models.Response
import com.github.mckernant1.lol.esports.api.lambda.models.SuccessResponse
import com.github.mckernant1.lol.esports.api.lambda.util.mapToObject

class GetAllLeagues : AbstractNoInputRequestHandler() {

    override fun execute(): Response {
        val items = ddb.scan(
            ScanRequest(LEAGUES_TABLE_NAME)
        ).items
            .asSequence()
            .map { ItemUtils.toItem(it).asMap() }
            .map { mapToObject(it, League::class) }
            .toList()

        return SuccessResponse(
            items,
            200,
        )
    }
}
