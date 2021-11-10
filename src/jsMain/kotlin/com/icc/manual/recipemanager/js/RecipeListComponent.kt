package com.icc.manual.recipemanager.js

import com.icc.manual.recipemanager.model.Recipe
import kotlinx.coroutines.launch
import react.dom.li
import react.dom.ul
import react.functionalComponent
import react.useEffect
import react.useState
import scope

val RecipeListComponent = functionalComponent<InputProps> {
    var recipeList by useState(emptyList<Recipe>())

    useEffect {
        scope.launch {
            recipeList = getRecipes()
        }
    }

    ul {
        recipeList.sortedBy(Recipe::name).forEach { recipe ->
            li {
                key = recipe.id.toString()
                +"${recipe.name}"
            }
        }
    }
}