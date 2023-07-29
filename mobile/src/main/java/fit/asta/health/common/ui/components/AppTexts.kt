package fit.asta.health.common.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

object AppTexts {

    @Composable
    fun DisplayLarge(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.displayLarge,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun DisplayMedium(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun DisplaySmall(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.displaySmall,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun HeadlineLarge(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun HeadlineMedium(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier,
            color = color
        )
    }


    @Composable
    fun HeadlineSmall(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun TitleLarge(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun TitleMedium(
        cardTitle: String,
        modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier,
            color = color
        )
    }


    @Composable
    fun TitleSmall(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun BodyLarge(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun BodyMedium(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun BodySmall(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun LabelLarge(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.labelLarge,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun LabelMedium(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.labelMedium,
            modifier = modifier,
            color = color
        )
    }

    @Composable
    fun LabelSmall(
        cardTitle: String,
        modifier: Modifier = Modifier,
        color: Color = MaterialTheme.colorScheme.onSurface,
    ) {
        Text(
            text = cardTitle,
            style = MaterialTheme.typography.labelSmall,
            modifier = modifier,
            color = color
        )
    }


}