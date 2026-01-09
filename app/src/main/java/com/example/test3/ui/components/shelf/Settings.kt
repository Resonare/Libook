package com.example.test3.ui.components.shelf

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.test3.R
import com.example.test3.ui.components.CircleButton

@Composable
fun Settings(
    isDarkTheme: Boolean,
    handleChangeDarkTheme: () -> Unit,
    handleClose: () -> Unit,
    innerPadding: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures { }
            }
            .padding(innerPadding)
            .padding(horizontal = 20.dp)
    ) {
        CircleButton(
            painter = painterResource(R.drawable.ic_arrow_left),
            onClick = {
                handleClose()
            }
        )

        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column (
                modifier = Modifier,

            ) {
                Row (
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(R.string.settings_title),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                        ),
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }

                Spacer(Modifier.height(40.dp))

                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = {
                            handleChangeDarkTheme()
                        }
                    )

                    Spacer(Modifier.width(15.dp))

                    Text(
                        text = stringResource(R.string.dark_theme_option),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }

            Text(
                modifier = Modifier.padding(20.dp),
                text = stringResource(R.string.version_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}