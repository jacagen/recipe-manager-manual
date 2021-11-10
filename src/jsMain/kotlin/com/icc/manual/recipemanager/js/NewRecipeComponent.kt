package com.icc.manual.recipemanager.js

import com.icc.manual.recipemanager.model.Recipe
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.RProps
import react.dom.*
import react.functionalComponent
import react.useState

external interface InputProps : RProps

private val scope = MainScope()

val NewRecipeComponent = functionalComponent<InputProps> {
    var newRecipe by useState<Recipe>(Recipe())

    table {

        /* Fields for new recipe */
        tr {
            table {
                /* Recipe name */
                tr {
                    td {
                        +"Name"  // TODO Internationalization
                    }
                    td {
                        input(InputType.text) {
                            attrs.onChangeFunction = {
                                val value = (it.target as HTMLInputElement).value
                                newRecipe = newRecipe.copy(name = value)
                            }
                            attrs.value = newRecipe.name ?: ""
                        }
                    }
                }

                /* Recipe decription */
                tr {
                    td {
                        +"Description"  // TODO Internationalization
                    }
                    td {
                        input(InputType.text) {
                            attrs.onChangeFunction = {
                                val value = (it.target as HTMLInputElement).value
                                newRecipe = newRecipe.copy(desc = value)
                            }
                            attrs.value = newRecipe.desc ?: ""
                        }
                    }
                }
            }
        }

        /* Submit button */
        tr {
            td {
                button(type = ButtonType.submit) {
                    +"Create"
                    attrs.onClickFunction = {
                        val recipeToSave = newRecipe
                        newRecipe = Recipe("", "")
                        scope.launch {
                            addRecipe(recipeToSave)
                        }
                    }
                }
            }
        }

    }
}