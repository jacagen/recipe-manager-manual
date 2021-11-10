package com.icc.manual.recipemanager.js

import kotlinx.html.ButtonType
import react.*
import react.dom.*
import kotlinx.html.js.*
import org.w3c.dom.events.Event

external interface ListAndDetailProps : RProps

val ListAndDetailComponent = functionalComponent<ListAndDetailProps> {
    val plusClickHandler: (Event) -> Unit = {
        console.log("Button clicked")
    }

    // TODO Is table really the right way to structure this?
    table {
        tbody {
            tr {
                td {
                    form {
                        button(type = ButtonType.button) {
                            attrs.onClickFunction = plusClickHandler
                            +"+"
                        }
                    }
                }
                td {
                    child(NewRecipeComponent)
                }
            }
        }
    }


}