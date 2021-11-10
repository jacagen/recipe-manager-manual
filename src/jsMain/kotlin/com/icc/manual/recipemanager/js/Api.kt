package com.icc.manual.recipemanager.js

import com.icc.manual.recipemanager.model.Recipe
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.window

const val recipeEndpoint = "/recipe"

val endpoint = window.location.origin // only needed until https://youtrack.jetbrains.com/issue/KTOR-453 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

suspend fun addRecipe(recipe: Recipe) {
    jsonClient.post<Unit>(endpoint + recipeEndpoint) {
        contentType(ContentType.Application.Json)
        body = recipe

    }
}