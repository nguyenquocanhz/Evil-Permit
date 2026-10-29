package vn.gemssoftware.evilpermit.ui.theme

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TokensContrastTest {

    private val palettes = mapOf("dark" to EpTokens.Dark, "light" to EpTokens.Light)

    /** Text pairs that must reach WCAG AA for normal text (4.5:1). */
    private fun EpPalette.textPairs(): Map<String, Pair<Long, Long>> = mapOf(
        "fg on bg" to (fg to bg),
        "fg on card" to (fg to card),
        "muted on bg" to (muted to bg),
        "muted on card" to (muted to card),
        "onAccent on accent" to (onAccent to accent),
        "onStatus on good" to (onStatus to good),
        "onStatus on warn" to (onStatus to warn),
        "onStatus on crit" to (onStatus to crit),
        "good on card" to (good to card),
        "warn on card" to (warn to card),
        "crit on card" to (crit to card),
    )

    @Test
    fun textPairsMeetWcagAa() {
        val failures = palettes.flatMap { (theme, palette) ->
            palette.textPairs().mapNotNull { (name, pair) ->
                val ratio = contrastRatio(pair.first, pair.second)
                if (ratio < 4.5) "$theme: $name = ${"%.2f".format(ratio)}" else null
            }
        }
        assertTrue("Contrast below 4.5:1 -> $failures", failures.isEmpty())
    }

    @Test
    fun contrastRatioMatchesKnownValues() {
        assertEquals(21.0, contrastRatio(0xFF000000, 0xFFFFFFFF), 0.01)
        assertEquals(1.0, contrastRatio(0xFF2F6FE0, 0xFF2F6FE0), 0.0001)
    }
}
