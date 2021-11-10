import com.icc.manual.recipemanager.js.ListAndDetailComponent
import kotlinx.coroutines.MainScope
import react.RProps
import react.child
import react.functionalComponent

private val scope = MainScope()

val App = functionalComponent<RProps> {
    child(
        ListAndDetailComponent,
    )
}