package net.devrob.arkanotest.presentation.characterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import net.devrob.arkanotest.R
import net.devrob.arkanotest.domain.model.CharacterDetail
import net.devrob.arkanotest.domain.model.CharacterStatus
import net.devrob.arkanotest.domain.model.LocationInfo
import net.devrob.arkanotest.presentation.components.CharacterStatusBadge
import net.devrob.arkanotest.presentation.components.InfoSection

@Composable
fun CharacterDetailContent(
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
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "Character card for ${character.name}"
                },
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                contentColor = MaterialTheme.colorScheme.surface
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
                            .memoryCacheKey(character.image)
                            .diskCacheKey(character.image)
                            .build(),
                        contentDescription = "Image of ${character.name}",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.placeholder_character),
                        error = painterResource(id = R.drawable.error_character)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                                    )
                                )
                            )
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
                            if (character.gender.isNotBlank() && character.gender.lowercase() != "unknow") {
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

                    InfoSection(
                        icon = Icons.Outlined.Public,
                        label = "Origin",
                        value = character.origin.name,
                        iconTint = statusColor
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InfoSection(
                        icon = Icons.Outlined.LocationOn,
                        label = "Last Know Location",
                        value = character.location.name,
                        iconTint = statusColor
                    )

                    if (character.type.isNotBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))

                        InfoSection(
                            icon = Icons.Outlined.Person,
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
@Preview(showBackground = true, showSystemUi = true)
fun PreviewCharacterDetail() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CharacterDetailContent(
            character = CharacterDetail(
                id = 1,
                name = "Rick",
                status = CharacterStatus.ALIVE,
                species = "Human",
                type = "Male",
                gender = "Male",
                origin = LocationInfo(
                    name = "Earth",
                    url = ""
                ),
                location = LocationInfo(
                    name = "Earth",
                    url = ""
                ),
                image = "",
                episodeCount = 52,
                created = "2017-11-04T18:48:46.250Z"
            )
        )
    }
}