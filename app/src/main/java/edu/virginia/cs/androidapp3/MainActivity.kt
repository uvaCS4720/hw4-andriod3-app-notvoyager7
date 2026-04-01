package edu.virginia.cs.androidapp3

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.virginia.cs.androidapp3.ui.theme.AndroidApp3Theme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidApp3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "Welcome to the Counter App!"
        )
        Spacer(modifier = modifier.height(16.dp))
        Counter(viewModel)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewMainScreen() {
    AndroidApp3Theme {
        MainScreen(viewModel = MainViewModel())
    }
}

@Composable
fun Counter(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val counterValue = uiState.counterValue
    Row {
        Text("Value: $counterValue")
        Button( // increment button
            onClick = { viewModel.incrementCounter() },
            modifier = modifier
        ) { Text("+") }
        Button( //decrement button
            onClick = { viewModel.decrementCounter() },
            enabled = viewModel.isDecrementEnabled,
            modifier = modifier
        ) {
            Text("-")
        }
        Button( // reset button
            onClick = { viewModel.incrementCounter() },
            enabled = viewModel.isResetEnabled,
            modifier = modifier
        ) {
            Text("Reset")
        }

    }
}


@Preview(name = "Light Mode Counter", showBackground = true)
@Preview(name = "Dark Mode Counter", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CounterPreview() {
    AndroidApp3Theme {
        Counter(viewModel = MainViewModel(0))
    }
}