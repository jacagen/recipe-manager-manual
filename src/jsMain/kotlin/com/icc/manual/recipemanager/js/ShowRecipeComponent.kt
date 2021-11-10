package com.icc.manual.recipemanager.js

import com.icc.manual.recipemanager.model.Recipe
import kotlinx.coroutines.launch
import react.RProps
import react.dom.table
import react.dom.tbody
import react.dom.td
import react.dom.tr
import react.functionalComponent
import react.useEffect
import react.useState
import scope

external interface ShowProps : RProps {
    var id: Int?
}

val ShowRecipeComponent = functionalComponent<ShowProps> { props ->
    var recipe by useState(Recipe())

    useEffect(props) { // TODO Figure out why this is deprecated
        scope.launch {
            val id = props.id
            recipe = if (id == null) Recipe() else getRecipe(id)
        }
    }

    table {
        tbody {
            /* Recipe name */
            tr {
                td {
                    +"Name"  // TODO Internationalization
                }
                td {
                    +recipe.name
                }
            }

            /* Recipe description */
            tr {
                td {
                    +"Description"  // TODO Internationalization
                }
                td {
                    +(recipe.desc ?: "")
                }
            }
        }
    }
}