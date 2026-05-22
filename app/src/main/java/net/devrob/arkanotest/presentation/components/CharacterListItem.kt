package net.devrob.arkanotest.presentation.components

import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import net.devrob.arkanotest.R
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.domain.model.CharacterStatus

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .semantics {
                contentDescription = "Character: ${character.name}"
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        CharacterImage(
            characterName = character.name,
            urlImage = character.imageUrl
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = character.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(8.dp))
            CharacterStatusBadge(character.status)
        }
    }
}

@Composable
fun CharacterImage(
    urlImage: String,
    characterName: String
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(urlImage)
            .crossfade(true)
            .memoryCacheKey(urlImage)
            .diskCacheKey(urlImage)
            .build(),
        contentDescription = "Image of $characterName",
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.FillHeight,
        placeholder = painterResource(id = R.drawable.placeholder_character),
        error = painterResource(id = R.drawable.error_character)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharacterListItemPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CharacterItem(
            Character(
                id = 1,
                name = "Character Name",
                status = CharacterStatus.UNKNOWN,
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            )
        )
    }

}
