package com.example.lifecalculator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.round

@Composable
fun MainScreen(modifier: Modifier) {

    var age by remember { mutableStateOf("0") }
    val result by remember { derivedStateOf { calculate(age) } }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = age.removePrefix("0"),
            onValueChange = { age = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            label = { Text(text = stringResource(R.string.age_hint)) },
        )
        DrawRect(result = result, age = if (age.isValidAge) age.toInt() else 0)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DrawRect(result: Int, age: Int) {

    val num = (1..100).toList()

    LazyVerticalGrid(
        cells = GridCells.Fixed(10),
        modifier = Modifier.padding(10.dp)
    ) {
        itemsIndexed(num) { index, _ ->
            Box(
                modifier = Modifier
                    .padding(1.dp)
                    .height(30.dp)
                    .background(
                        color =
                        drawBackground(index, result, age)
                    )
                    .clip(RectangleShape)
                    .border(1.dp, Color.White)
            )
        }
    }
}

private fun calculate(userAge: String, averageAge: Double = 80.0): Int =
    if (userAge.isValidAge) {
        round(userAge.toDouble() * 100 / averageAge).toInt()
    } else {
        0
    }

private fun drawBackground(index: Int, result: Int, age: Int): Color {
    return when {
        index < result && age <= 7 -> Color.Green
        index < result && age <= 17 -> Color.Blue
        index < result && age >= 18 -> Color.Red
        else -> {
            Color.LightGray
        }
    }
//  when(index) {
//      in 0..7 -> Color.Green
//      in 8..17 -> Color.Blue
//      in 17..1000 -> Color.Red
//      else -> Color.Gray
// }
}

val String.isValidAge get(): Boolean = isNotBlank()

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(modifier = Modifier)
}