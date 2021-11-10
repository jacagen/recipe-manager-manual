package com.icc.manual.recipemanager.js

import com.icc.manual.recipemanager.model.Recipe
import kotlinx.coroutines.launch
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.RProps
import react.dom.*
import react.functionalComponent
import react.useState
import scope

external interface InputProps : RProps {
    var onCreatedNewRecipe: (Int) -> Unit
}

val NewRecipeComponent = functionalComponent<InputProps> { props ->
    var newRecipe by useState(Recipe())

    table {
        tbody {

            /* Fields for new recipe */
            tr {
                td {
                    table {
                        tbody {
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

                            /* Recipe description */
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
                                props.onCreatedNewRecipe(recipeToSave.id)
                            }
                        }
                    }
                }
            }
        }
    }
}