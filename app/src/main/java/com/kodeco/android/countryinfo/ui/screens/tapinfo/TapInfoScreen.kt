package com.kodeco.android.countryinfo.ui.screens.tapinfo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.ui.components.AppBar
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

@Composable
fun TapInfoScreen(
    viewModel: TapInfoViewModel,
) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        AppBar(
            title = stringResource(R.string.tap_info_screen),
        )
    }) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.tap_flows, state.tapCount),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = stringResource(R.string.back_flows, state.backCount),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))

            UptimeCounterAnimation(counter = state.counter)
        }
    }
}

@Composable
fun UptimeCounterAnimation(counter: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.uptime_flows),
            style = MaterialTheme.typography.titleMedium,
        )
        AnimatedContent(
            label = "Animated Counter",
            targetState = counter,
            transitionSpec = {
                slideInVertically { it }
                    .togetherWith(
                        slideOutVertically { -it }
                    )
            },

            ) { counter ->
            Text(
                text = "$counter",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 8.dp, end = 4.dp)
            )
        }
        Text(
            text = stringResource(R.string.sec),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TapInfoPreview() {
    MyApplicationTheme {
        TapInfoScreen(viewModel = viewModel())
    }
}

@Preview(showBackground = true)
@Composable
fun UptimeCounterAnimationPreview() {
    MyApplicationTheme {
        UptimeCounterAnimation(counter = 10)
    }
}
