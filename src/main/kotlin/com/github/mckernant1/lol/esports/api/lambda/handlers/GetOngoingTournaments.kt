package com.github.mckernant1.lol.esports.api.lambda.handlers

import com.amazonaws.services.dynamodbv2.document.ItemUtils
import com.amazonaws.services.dynamodbv2.model.ScanRequest
import com.github.mckernant1.extensions.boolean.falseIfNull
import com.github.mckernant1.lol.esports.api.Tournament
import com.github.mckernant1.lol.esports.api.lambda.TOURNAMENTS_TABLE_NAME
import com.github.mckernant1.lol.esports.api.lambda.ddb
import com.github.mckernant1.lol.esports.api.lambda.models.Response
import com.github.mckernant1.lol.esports.api.lambda.models.SuccessResponse
import com.github.mckernant1.lol.esports.api.lambda.util.endDateAsDate
import com.github.mckernant1.lol.esports.api.lambda.util.mapToObject
import com.github.mckernant1.lol.esports.api.lambda.util.startDateAsDate
import java.util.Date

class GetOngoingTournaments : AbstractNoInputRequestHandler() {
    override fun execute(): Response {
        val tournys = ddb.scan(ScanRequest(TOURNAMENTS_TABLE_NAME))
            .items
            .asSequence()
            .map { ItemUtils.toItem(it).asMap() }
            .map { mapToObject(it, Tournament::class) }
            .filter {
                it.startDateAsDate()?.before(Date()).falseIfNull()
                        && it.endDateAsDate()?.after(Date()).falseIfNull()
            }.toList()

        return SuccessResponse(
            tournys,
            200
        )
    }
}
