package com.github.mckernant1.lol.esports.api.lambda.util

import com.github.mckernant1.lol.esports.api.lambda.gson
import kotlin.reflect.KClass

fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>) : T {
    return gson.fromJson(gson.toJson(map), clazz.java)
}
