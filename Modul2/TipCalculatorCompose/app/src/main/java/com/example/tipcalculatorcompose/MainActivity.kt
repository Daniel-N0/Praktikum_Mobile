package com.example.tipcalculatorcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.background)
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorScreen() {
    var amountInput by rememberSaveable { mutableStateOf("") }
    var selectedTipPercent by rememberSaveable { mutableDoubleStateOf(0.15) }
    var roundUp by rememberSaveable { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val tipOptions = listOf("15%" to 0.15, "18%" to 0.18, "20%" to 0.20)

    val formattedTip by remember(amountInput, selectedTipPercent, roundUp) {
        derivedStateOf {
            calculateTip(amountInput, selectedTipPercent, roundUp)
        }
    }

    Column(
        modifier = Modifier
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(id = R.string.calculate_tip),
            color = colorResource(id = R.color.text_secondary),
            fontSize = 18.sp
        )

        TextField(
            value = amountInput,
            onValueChange = { newValue ->
                val clean = newValue.replace("[^\\d.,]".toRegex(), "")
                val separatorCount = clean.count { it == '.' || it == ',' }
                if (separatorCount <= 1) {
                    amountInput = clean
                }
            },
            label = { Text(stringResource(id = R.string.bill_amount)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_money),
                    contentDescription = null,
                    tint = colorResource(id = R.color.icon_color)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = tipOptions.firstOrNull { it.second == selectedTipPercent }?.first ?: "15%",
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(id = R.string.tip_percentage)) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_percent),
                        contentDescription = null,
                        tint = colorResource(id = R.color.icon_color)
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tipOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.first) },
                        onClick = {
                            selectedTipPercent = option.second
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.round_up_tip),
                fontSize = 16.sp
            )
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(id = R.color.accent),
                    checkedTrackColor = colorResource(id = R.color.icon_color)
                )
            )
        }

        Text(
            text = stringResource(id = R.string.tip_amount_label) + " " + formattedTip,
            fontSize = 43.sp,
            lineHeight = 50.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.text_primary),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

fun calculateTip(amount: String, percent: Double, roundUp: Boolean): String {
    val cost = amount.replace(",", ".").toDoubleOrNull() ?: 0.0
    var tip = cost * percent

    if (roundUp) tip = kotlin.math.ceil(tip)

    return NumberFormat.getCurrencyInstance(Locale.US).format(tip)
}
