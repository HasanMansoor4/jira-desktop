import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.TokenManager
import kotlinx.coroutines.launch

@Composable
@Preview
fun TokenScreen() {
    var token by remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = token,
                    onValueChange = {
                        token = it
                    },
                    label = { Text(text = "Token") }
                )
                Button(onClick = {
                    scope.launch {
                        TokenManager.writeToken(token.text)
                    }
                }) {
                    Text("Save Token")
                }
            }
        }
    }
}

@Composable
fun OtherScreen() {
    MaterialTheme {
        Text("Hello Compose!")
    }
}

fun main() = application {
    var token by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        TokenManager.getTokenFlow().collect {
            token = it
        }
    }
    Window(onCloseRequest = ::exitApplication) {
        if (token.isNullOrBlank()) {
            TokenScreen()
        } else {
            OtherScreen()
        }
    }
}
