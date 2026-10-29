package vn.gemssoftware.evilpermit.ui.theme

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

/**
 * Colour tokens shared with the design canvas, as ARGB longs so they can be checked
 * in plain JVM unit tests without Compose.
 */
internal data class EpPalette(
    val bg: Long,
    val card: Long,
    val fg: Long,
    val muted: Long,
    val line: Long,
    val accent: Long,
    val onAccent: Long,
    val good: Long,
    val warn: Long,
    val crit: Long,
    val onStatus: Long,
)

internal object EpTokens {
    val Dark = EpPalette(
        bg = 0xFF0E1013,
        card = 0xFF1B1E23,
        fg = 0xFFEEF1F5,
        muted = 0xFF9AA3AD,
        line = 0xFF2A2E35,
        accent = 0xFF7AB4FF,
        onAccent = 0xFF0E1013,
        good = 0xFF5FD18F,
        warn = 0xFFF0B85A,
        crit = 0xFFFF8B80,
        onStatus = 0xFF0E1013,
    )

    val Light = EpPalette(
        bg = 0xFFF2F3F5,
        card = 0xFFFFFFFF,
        fg = 0xFF121417,
        muted = 0xFF5F6873,
        line = 0xFFE3E6EA,
        accent = 0xFF2F6FE0,
        onAccent = 0xFFFFFFFF,
        good = 0xFF1A7A48,
        warn = 0xFFA3660A,
        crit = 0xFFC8372D,
        onStatus = 0xFFFFFFFF,
    )
}

/** WCAG 2.x relative luminance of an opaque ARGB colour. */
internal fun relativeLuminance(argb: Long): Double {
    fun channel(shift: Int): Double {
        val c = ((argb shr shift) and 0xFF).toDouble() / 255.0
        return if (c <= 0.03928) c / 12.92 else ((c + 0.055) / 1.055).pow(2.4)
    }
    return 0.2126 * channel(16) + 0.7152 * channel(8) + 0.0722 * channel(0)
}

/** WCAG contrast ratio between two opaque colours, from 1.0 to 21.0. */
internal fun contrastRatio(a: Long, b: Long): Double {
    val la = relativeLuminance(a)
    val lb = relativeLuminance(b)
    return (max(la, lb) + 0.05) / (min(la, lb) + 0.05)
}
