package com.kodeco.android.countryinfo.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kodeco.android.countryinfo.R

@Composable
fun SplashScreen(
    navigateTo: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.world))

        val logoAnimationState = animateLottieCompositionAsState(composition = composition)

        LottieAnimation(
            composition = composition,
            progress = { logoAnimationState.progress }
        )

        if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
            navigateTo()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen {}
}
