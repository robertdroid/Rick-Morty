package net.devrob.arkanotest.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.devrob.arkanotest.domain.model.CharacterStatus

@Composable
fun CharacterStatusBadge(
    status: CharacterStatus,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (status) {
        CharacterStatus.ALIVE -> Color(0xFF4CAF50)
        CharacterStatus.DEAD -> Color(0xFFF44336)
        CharacterStatus.UNKNOWN -> Color(0xFF9E9E9E)
    }

    val statusText = when (status) {
        CharacterStatus.ALIVE -> "Alive"
        CharacterStatus.DEAD -> "Dead"
        CharacterStatus.UNKNOWN -> "Unknown"
    }

    Box(
        modifier = modifier
            .background(
                backgroundColor,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .semantics {
                contentDescription = "Status: $statusText"
            }
    ) {
        Text(
            text = statusText,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharacterStatusBadgePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            CharacterStatusBadge(
                CharacterStatus.ALIVE
            )
            Spacer(modifier = Modifier.height(8.dp))
            CharacterStatusBadge(
                CharacterStatus.DEAD
            )
            Spacer(modifier = Modifier.height(8.dp))
            CharacterStatusBadge(
                CharacterStatus.UNKNOWN
            )
        }
    }
}