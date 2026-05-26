package net.devrob.arkanotest.presentation.characterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.compose.AsyncImage
import coil.request.ImageRequest
import net.devrob.arkanotest.domain.model.CharacterDetail
import net.devrob.arkanotest.domain.model.CharacterStatus
import net.devrob.arkanotest.presentation.components.CharacterStatusBadge
import net.devrob.arkanotest.presentation.components.ErrorState
import net.devrob.arkanotest.presentation.components.LoadingState
import net.devrob.arkanotest.testutil.CharacterDetailTestFactory
import net.devrob.arkanotest.ui.theme.ArkanoTestTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_displaysProgressIndicator() {
        composeTestRule.setContent {
            ArkanoTestTheme() {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Loading,
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Loading content")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysCharacterName() {
        val character = CharacterDetailTestFactory.createRickSanchez()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Rick Sanchez")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysCharacterImage() {
        val character = CharacterDetailTestFactory.createRickSanchez()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Image of Rick Sanchez")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysStatusBadge() {
        val character = CharacterDetailTestFactory.createRickSanchez()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Alive")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysDeadStatusBadge() {
        val character = CharacterDetailTestFactory.createDeadCharacter()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Dead")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysUnknownStatusBadge() {
        val character = CharacterDetailTestFactory.createUnknownStatusCharacter()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Unknown")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysOrigin() {
        val character = CharacterDetailTestFactory.createRickSanchez()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Origin")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Earth (C-137)")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysLocation() {
        val character = CharacterDetailTestFactory.createRickSanchez()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Last Known Location")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Citadel of Ricks")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysSpeciesAndGender() {
        val character = CharacterDetailTestFactory.createRickSanchez()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Human - Male")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysEpisodeCount() {
        val character = CharacterDetailTestFactory.createRickSanchez()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Appeared in 51 episodes")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysEpisodeCountSingular() {
        val character = CharacterDetailTestFactory.create(episodeCount = 1)

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Appeared in 1 episode")
            .assertIsDisplayed()
    }

    @Test
    fun errorState_displaysErrorMessage() {
        val errorMessage = "Something went wrong"

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Error(errorMessage),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }

    @Test
    fun errorState_retryButtonCallsRetry() {
        var retryClicked = false

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Error("Error"),
                    onNavigateBack = {},
                    onRetry = { retryClicked = true }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Retry")
            .performClick()

        assertTrue(retryClicked)
    }

    @Test
    fun backButton_callsOnNavigateBack() {
        var backClicked = false

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Loading,
                    onNavigateBack = { backClicked = true },
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Navigate back")
            .performClick()

        assertTrue(backClicked)
    }

    @Test
    fun topAppBar_displaysCharacterNameInSuccessState() {
        val character = CharacterDetailTestFactory.createRickSanchez()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Rick Sanchez")
            .assertIsDisplayed()
    }

    @Test
    fun topAppBar_displaysDefaultTitleInLoadingState() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Loading,
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Character Details")
            .assertIsDisplayed()
    }

    @Test
    fun topAppBar_displaysDefaultTitleInErrorState() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Error("Error"),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Character Details")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysTypeWhenPresent() {
        val character = CharacterDetailTestFactory.createWithType()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterDetailScreenContent(
                    uiState = CharacterDetailUiState.Success(character),
                    onNavigateBack = {},
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Type")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Royalty")
            .assertIsDisplayed()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterDetailScreenContent(
    uiState: CharacterDetailUiState,
    onNavigateBack: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (uiState) {
                            is CharacterDetailUiState.Success -> uiState.character.name
                            else -> "Character Details"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is CharacterDetailUiState.Loading -> {
                LoadingState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
            is CharacterDetailUiState.Error -> {
                ErrorState(
                    message = uiState.message,
                    onRetry = onRetry,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
            is CharacterDetailUiState.Success -> {
                CharacterDetailContentForTest(
                    character = uiState.character,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun CharacterDetailContentForTest(
    character: CharacterDetail,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val statusColor = when (character.status) {
        CharacterStatus.ALIVE -> Color(0xFF4CAF50)
        CharacterStatus.DEAD -> Color(0xFFF44336)
        CharacterStatus.UNKNOWN -> Color(0xFF9E9E9E)
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(character.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Image of ${character.name}",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        CharacterStatusBadge(status = character.status)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 24.dp)
                ) {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = buildString {
                            append(character.species)
                            if (character.gender.isNotBlank() && character.gender.lowercase() != "unknown") {
                                append(" - ${character.gender}")
                            }
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        thickness = 1.dp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    InfoSectionForTest(
                        label = "Origin",
                        value = character.origin.name,
                        iconTint = statusColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InfoSectionForTest(
                        label = "Last Known Location",
                        value = character.location.name,
                        iconTint = statusColor
                    )

                    if (character.type.isNotBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))

                        InfoSectionForTest(
                            label = "Type",
                            value = character.type,
                            iconTint = statusColor
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Appeared in ${character.episodeCount} episode${if (character.episodeCount != 1) "s" else ""}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoSectionForTest(
    label: String,
    value: String,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconTint.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Public,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = iconTint
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}