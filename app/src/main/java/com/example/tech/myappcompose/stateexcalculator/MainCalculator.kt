package com.example.tech.myappcompose.stateexcalculator

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tech.myappcompose.R
import com.example.tech.myappcompose.ui.theme.MyAppComposeTheme
import com.example.tech.myappcompose.ui.theme.TipTimeTheme
import java.text.NumberFormat

private const val TAG = "MainCalculator"

private fun calculateTip(amount: Double, roundUp: Boolean, tipPercent: Double = 15.0): String {
    var tip = tipPercent / 100 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Composable
fun TipTimeLayout() {
    var amountInput by remember { mutableStateOf("") }
//    var amountInput = mutableStateOf("")
//    var amountInput = remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    val tip = if (tipPercent > 0) calculateTip(amount, roundUp, tipPercent) else calculateTip(amount, roundUp)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = R.string.bill_amount,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField(
            value = tipInput,
            onValueChange = { tipInput = it },
            label = R.string.how_was_the_service,
            placeholder = "15%",
            imeAction = ImeAction.Done,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    imeAction: ImeAction,
    modifier: Modifier = Modifier,
    placeholder: String = ""
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(label)) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        modifier = modifier
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        TipTimeLayout()
    }
}
