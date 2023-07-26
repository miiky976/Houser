package com.miiky.houser.ui.screens.crap

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.miiky.houser.ui.spacing
import com.udojava.evalex.Expression

@Composable
fun Calculator(
    modifier: Modifier = Modifier,
) {
    val result = remember { mutableStateOf("") }
    val calcul = remember { mutableStateOf("") }
    val buttons = listOf(
        CalculatorButtonItem("√") {calcul.value += "sqrt("},
        CalculatorButtonItem("^") { calcul.value += "^" },
        CalculatorButtonItem("cos") { calcul.value += "cos(" },
        CalculatorButtonItem("sin") { calcul.value += "sin(" },
        CalculatorButtonItem("AC") {
            calcul.value = ""
            result.value = ""
        },
        CalculatorButtonItem("(") { calcul.value += "(" },
        CalculatorButtonItem(")") { calcul.value += ")" },
        CalculatorButtonItem("/") { calcul.value += "/" },
        CalculatorButtonItem("7") { calcul.value += "7" },
        CalculatorButtonItem("8") { calcul.value += "8" },
        CalculatorButtonItem("9") { calcul.value += "9" },
        CalculatorButtonItem("*") { calcul.value += "*" },
        CalculatorButtonItem("4") { calcul.value += "4" },
        CalculatorButtonItem("5") { calcul.value += "5" },
        CalculatorButtonItem("6") { calcul.value += "6" },
        CalculatorButtonItem("-") { calcul.value += "-" },
        CalculatorButtonItem("1") { calcul.value += "1" },
        CalculatorButtonItem("2") { calcul.value += "2" },
        CalculatorButtonItem("3") { calcul.value += "3" },
        CalculatorButtonItem("+") { calcul.value += "+" },
        CalculatorButtonItem("0") { calcul.value += "0" },
        CalculatorButtonItem(".") { calcul.value += "." },
        CalculatorButtonItem("del") {
            calcul.value = calcul.value.dropLast(1)
        },
        CalculatorButtonItem("=") {
            try {
                result.value = Expression(calcul.value).eval().toString()
            }catch (e: ArithmeticException){
                result.value = e.message.toString()
            }catch (e: Exception){
                result.value = e.message.toString()
            }
        },
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing / 2)
            .fillMaxHeight()
            .fillMaxWidth()
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = result.value,
            modifier = modifier.padding(vertical = spacing),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = calcul.value,
            modifier = modifier.padding(vertical = spacing),
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = modifier.weight(1f))
        LazyVerticalGrid(columns = GridCells.Fixed(4)) {
            this.items(buttons) {
                ElevatedButton(
                    onClick = { it.action() }, modifier = modifier.padding(spacing / 4),
                ) {
                    Text(text = it.title, fontSize = 42.sp)
                }
            }
        }
    }
}

data class CalculatorButtonItem(val title: String, val action: () -> Unit)