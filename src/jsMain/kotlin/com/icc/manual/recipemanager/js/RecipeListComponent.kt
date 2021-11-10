package com.icc.manual.recipemanager.js

import com.icc.manual.recipemanager.model.Recipe
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RProps
import react.dom.li
import react.dom.ul
import react.functionalComponent

external interface ListProps : RProps {
    var onRecipeClick: (Int) -> Unit
    var recipes: List<Recipe>
}

val RecipeListComponent = functionalComponent<ListProps> { props ->

    val clickHandler: (Int) -> ((Event) -> Unit) = { key ->
        {
            props.onRecipeClick(key)
        }
    }

    ul {
        props.recipes.sortedBy(Recipe::name).forEach { recipe ->
            li {
                key = recipe.id.toString()
                +recipe.name
                attrs.onClickFunction = clickHandler(recipe.id)
            }
        }
    }
}