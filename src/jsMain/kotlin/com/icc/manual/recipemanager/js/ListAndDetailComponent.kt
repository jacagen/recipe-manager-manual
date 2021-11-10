package com.icc.manual.recipemanager.js

import com.icc.manual.recipemanager.model.Recipe
import kotlinext.js.jsObject
import kotlinx.coroutines.launch
import kotlinx.html.ButtonType
import kotlinx.html.hidden
import react.*
import react.dom.*
import kotlinx.html.js.*
import org.w3c.dom.events.Event
import scope

external interface ListAndDetailProps : RProps

enum class Display { NONE, VIEW, NEW }

val ListAndDetailComponent = functionalComponent<ListAndDetailProps> {
    var currentDisplay by useState(Display.NONE)
    var currentViewRecipe by useState<Int?>(null)
    var recipeList by useState(emptyList<Recipe>())

    useEffectOnce() { // TODO Figure out why this is deprecated
        console.log("Recipe list: $recipeList")
        scope.launch {
            recipeList = getRecipes()
        }
    }

    val plusClickHandler: (Event) -> Unit = {
        console.log("Button clicked")
        currentDisplay = Display.NEW
    }

    // TODO Is table really the right way to structure this?
    table {
        tbody {
            tr {

                /* Recipe list & "new" button */
                td {
                    table {
                        tbody {
                            /* Recipe list */
                            tr {
                                td {
                                    child(RecipeListComponent,
                                        props = jsObject {
                                            onRecipeClick = {
                                                console.log("Clicked on $it")
                                                currentViewRecipe = it
                                                currentDisplay = Display.VIEW
                                            }
                                            recipes = recipeList
                                        })
                                }
                            }

                            /* New button etc. */
                            tr {
                                td {
                                    form {
                                        button(type = ButtonType.button) {
                                            attrs.onClickFunction = plusClickHandler
                                            +"+"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                /* New recipe entry or show existing recipe*/
                td {
                    /* New recipe */
                    div {
                        attrs.hidden = (currentDisplay != Display.NEW)
                        child(NewRecipeComponent,
                            props = jsObject {
                                onCreatedNewRecipe = { id ->
                                    scope.launch {
                                        currentViewRecipe = id
                                        currentDisplay = Display.VIEW
                                        recipeList = getRecipes()
                                    }
                                }
                            })
                    }

                    /* Show recipe */
                    div {
                        attrs.hidden = (currentDisplay != Display.VIEW)
                        child(ShowRecipeComponent,
                            props = jsObject {
                                id = currentViewRecipe
                            }
                        )
                    }
                }
            }
        }
    }

}