package com.github.mckernant1.lol.esports.api.lambda

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient


internal const val MATCHES_TABLE_NAME = "Matches"

internal const val LEAGUES_TABLE_NAME = "Leagues"

internal const val PLAYERS_TABLE_NAME = "Players"
internal const val PLAYERS_TABLE_TEAM_INDEX = "teamId-id-index"

internal const val TEAMS_TABLE_NAME = "Teams"

internal const val TOURNAMENTS_TABLE_NAME = "Tournaments"

internal val ddb = AmazonDynamoDBClient.builder()
    .build()



