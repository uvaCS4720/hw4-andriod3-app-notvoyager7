package edu.virginia.cs.androidapp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.virginia.cs.androidapp3.ui.theme.AndroidApp3Theme
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class MainActivity : ComponentActivity() {
    // copied this singleton pattern from Professor McBurney's example in the Counters Lab
    // this fetches the single gameDao for the entire application, and it only fetches it when it is needed
    // (that is what the 'lazy' part is for)
    private val locationDao by lazy {
        val database = LocationDatabase.getDatabase(applicationContext)
        return@lazy database.locationDao()
    }

    // Gemini 3 Pro showed me how to create this
    // Linked me to the docs here: https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories
    val viewModel: MainViewModel by viewModels {
        viewModelFactory {
            initializer {
                MainViewModel(LocationRepository(locationDao, LocationApi.api))
            }
        }
    }

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
    Column(modifier = modifier.padding(start = 10.dp, end = 10.dp, top = 15.dp)) {
        Text(
            text = "Map View",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displayMedium,  // Gemini 3 Pro showed me how to set the font this way to avoid issues with line height
            color = MaterialTheme.colorScheme.secondary
        )

        val tag = remember { mutableStateOf("core") }
        MinimalDropdownMenu(
            // TODO: replace with the actual fetched list of unique tags, which will have to be persisted in the db
            options = listOf(
                "academic",
                "administration",
                "arts",
                "athletics",
                "bookstore",
                "business",
                "computer_science",
                "core",
                "dining",
                "education",
                "engineering",
                "entertainment",
                "health",
                "historic",
                "hospital",
                "humanities",
                "landmark",
                "law",
                "library",
                "nursing",
                "off_grounds",
                "outdoor",
                "parking",
                "public_policy",
                "recreation",
                "retail",
                "sciences",
                "services",
                "social_sciences",
                "student_life",
                "study",
                "tours",
                "transit",
                "transportation",
                "wellness"
            ),
            // Got this from google AI overview to capitalize first character when displaying
            // TODO: store this in the viewmodel (uiState)
            text = tag.value.replaceFirstChar { it.titlecase() },
            modifier = Modifier.padding(start = 5.dp),
            onClick = {
                tag.value = it
            },
            // TODO: have a loading state
//            disabled = uiState.value.loading
        )
    }
}

// Component from the official Compose docs:
// https://developer.android.com/develop/ui/compose/components/menu
// This modified version (with the styling) was generated by Claude Opus 4.6 Extended
@Composable
fun MinimalDropdownMenu(
    modifier: Modifier = Modifier,
    options: List<String> = listOf(),
    text: String = "Open Dropdown",
    onClick: (option: String) -> Unit,
    disabled: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val surfaceColor = MaterialTheme.colorScheme.surface
    val scrollbarColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

    Box(modifier = modifier) {
        OutlinedButton(
            onClick = { expanded = !expanded },
            enabled = !disabled,
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text)
            Spacer(Modifier.width(8.dp))
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            scrollState = scrollState,
            modifier = Modifier
                .heightIn(max = 300.dp)
                .drawWithContent {
                    drawContent()

                    val fadeHeight = 40.dp.toPx()
                    val canScrollUp = scrollState.value > 0
                    val canScrollDown = scrollState.value < scrollState.maxValue

                    // Top fade
                    if (canScrollUp) {
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(surfaceColor, Color.Transparent),
                                startY = 0f,
                                endY = fadeHeight
                            )
                        )
                    }

                    // Bottom fade
                    if (canScrollDown) {
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, surfaceColor),
                                startY = size.height - fadeHeight,
                                endY = size.height
                            )
                        )
                    }

                    // Scrollbar thumb
                    val totalScroll = scrollState.maxValue.toFloat()
                    if (totalScroll > 0f) {
                        val trackWidth = 3.dp.toPx()
                        val trackPadding = 4.dp.toPx()
                        val thumbMinHeight = 24.dp.toPx()

                        val visibleFraction = size.height / (size.height + totalScroll)
                        val thumbHeight = (size.height * visibleFraction)
                            .coerceIn(thumbMinHeight, size.height)
                        val thumbOffset = (scrollState.value / totalScroll) *
                                (size.height - thumbHeight)

                        drawRoundRect(
                            color = scrollbarColor,
                            topLeft = Offset(
                                x = size.width - trackWidth - trackPadding,
                                y = thumbOffset
                            ),
                            size = Size(trackWidth, thumbHeight),
                            cornerRadius = CornerRadius(trackWidth / 2f)
                        )
                    }
                }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.replaceFirstChar { it.titlecase() }) },
                    onClick = {
                        expanded = false
                        onClick(option)
                    }
                )
            }
        }
    }
}