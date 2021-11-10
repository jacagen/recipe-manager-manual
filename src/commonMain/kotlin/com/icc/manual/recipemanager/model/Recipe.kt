package com.icc.manual.recipemanager.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    var name: String? = null,
    var desc: String? = null,
) {
    val id: Int = name.hashCode() // TODO Need a GUID or something for this

    companion object {
        const val path = "/recipe"
    }
}