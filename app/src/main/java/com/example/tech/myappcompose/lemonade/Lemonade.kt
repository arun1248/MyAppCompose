package com.example.tech.myappcompose.lemonade

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tech.myappcompose.R

@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {

    var lemonadeState by remember { mutableStateOf(1) }
    var squeezeState by remember { mutableStateOf((2..4).random()) }

    var imageResource = R.drawable.lemon_tree
    var textInfo = "Tap the lemon tree to select a lemon"

    when (lemonadeState) {
        1 -> {
            imageResource = R.drawable.lemon_tree
            textInfo = "Tap the lemon tree to select a lemon"
        }

        2 -> {
            squeezeState = (2..4).random()
            imageResource = R.drawable.lemon_squeeze
            textInfo = "Keep tapping the lemon to squeeze it"
        }

        3 -> {
            imageResource = R.drawable.lemon_drink
            textInfo = "Tap the lemonade to drink it"
        }

        4 -> {
            imageResource = R.drawable.lemon_restart
            textInfo = "Tap the empty glass to start again"
        }
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFfede0d)),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Lemonade",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(16.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var clicks = 1
            Button(
                onClick = {
                    if (lemonadeState in 1..3) {
                        if (lemonadeState == 2) {
                            if (clicks == squeezeState) {
                                lemonadeState++
                                return@Button
                            } else {
                                clicks++
                                return@Button
                            }
                        }

                        lemonadeState++
                    } else {
                        lemonadeState = 1
                    }
                },
                shape = ShapeDefaults.ExtraLarge,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Image(
                    painter = painterResource(imageResource),
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = textInfo
            )
        }

    }
}

@Preview
@Composable
private fun LemonadeAppPreview() {
    LemonadeApp(
        modifier = Modifier.fillMaxSize()
    )
}