import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.App
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Quantity Calc",
    ) {
        window.minimumSize = Dimension(400, 780)
        App()
    }
}