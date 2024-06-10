package com.kodeco.android.countryinfo.ui.screens.tapinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

@Composable
fun TapInfo(
    viewModel: TapInfoViewModel,
    onRefresh: () -> Unit = {}
) {
    val tapFlows by viewModel.tapFlow.collectAsState()
    val backFlows by viewModel.backFlow.collectAsState()
    val counterFlows by viewModel.counterFlow.collectAsState()

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
        TapInfo(viewModel = viewModel())
    }
}
