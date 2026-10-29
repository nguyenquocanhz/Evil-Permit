package vn.gemssoftware.evilpermit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/** Status colours that Material's scheme has no slot for. */
@Immutable
data class EpStatusColors(
    val good: Color,
    val warn: Color,
    val crit: Color,
    val onStatus: Color,
)

val LocalEpStatusColors = staticCompositionLocalOf {
    EpStatusColors(Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified)
}

private fun EpPalette.toColorScheme(dark: Boolean): ColorScheme {
    val bg = Color(bg)
    val card = Color(card)
    val fg = Color(fg)
    val muted = Color(muted)
    val line = Color(line)
    val accent = Color(accent)
    val onAccent = Color(onAccent)
    val crit = Color(crit)
    val onStatus = Color(onStatus)
    return if (dark) {
        darkColorScheme(
            primary = accent, onPrimary = onAccent,
            background = bg, onBackground = fg,
            surface = bg, onSurface = fg,
            surfaceContainer = card, surfaceContainerHigh = card,
            onSurfaceVariant = muted, outline = muted, outlineVariant = line,
            error = crit, onError = onStatus,
        )
    } else {
        lightColorScheme(
            primary = accent, onPrimary = onAccent,
            background = bg, onBackground = fg,
            surface = bg, onSurface = fg,
            surfaceContainer = card, surfaceContainerHigh = card,
            onSurfaceVariant = muted, outline = muted, outlineVariant = line,
            error = crit, onError = onStatus,
        )
    }
}

private fun EpPalette.toStatusColors() = EpStatusColors(
    good = Color(good),
    warn = Color(warn),
    crit = Color(crit),
    onStatus = Color(onStatus),
)

@Composable
fun EvilPermitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val palette = if (darkTheme) EpTokens.Dark else EpTokens.Light
    CompositionLocalProvider(
        LocalEpStatusColors provides palette.toStatusColors(),
    ) {
        MaterialTheme(
            colorScheme = palette.toColorScheme(darkTheme),
            typography = EpTypography,
            content = content,
        )
    }
}
