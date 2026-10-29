package vn.gemssoftware.evilpermit.ui.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween

/** One UI basic interpolator: fast start, long settle. */
val OneUiEasing = CubicBezierEasing(0.22f, 0.25f, 0.00f, 1.00f)

/** One UI motion stays between 100 ms and 500 ms. */
fun <T> oneUiTween(durationMillis: Int = 250): TweenSpec<T> =
    tween(durationMillis = durationMillis.coerceIn(100, 500), easing = OneUiEasing)
