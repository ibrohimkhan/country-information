package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.flow.Flows
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

@Composable
fun TapInfo(
    onRefresh: () -> Unit = {}
) {
    var tapFlows by rememberSaveable { mutableIntStateOf(0) }
    var backFlows by rememberSaveable { mutableIntStateOf(0) }
    var counterFlows by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(key1 = "tapFlows") {
        Flows.tapFlow.collect {
            tapFlows = it
        }
    }

    LaunchedEffect(key1 = "backFlows") {
        Flows.backFlow.collect {
            backFlows = it
        }
    }

    LaunchedEffect(key1 = "counterFlows") {
        Flows.counterFlow.collect {
            counterFlows = it
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.tap_flows, tapFlows),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )

            Button(
                onClick = {
                    onRefresh()
                },
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.refresh_btn),
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Text(
                text = stringResource(R.string.back_flows, backFlows),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
        }

        Text(
            text = stringResource(R.string.uptime_flows, counterFlows),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TapInfoPreview() {
    MyApplicationTheme {
        TapInfo()
    }
}