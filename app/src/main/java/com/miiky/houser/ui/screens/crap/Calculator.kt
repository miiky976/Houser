package com.miiky.houser.ui.screens.crap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.miiky.houser.ui.spacing

@Composable
fun Calculator(
    modifier: Modifier = Modifier,
) {
    val buttonValues = listOf(
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "0", ".", "=", "+",
        "1", "2", "3", "-",
    )
    val buttons = listOf(
        Button(onClick = { /*TODO*/ }) {
            Text(text = "")
        }
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing / 2)
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Resultado de la calculadora",
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier
                .padding(vertical = spacing)
                .fillMaxSize()
        )
        LazyVerticalGrid(columns = GridCells.Fixed(4)){
            this.items(buttonValues){
                Button(onClick = { /*TODO*/ }, modifier = modifier.padding(spacing/2),
                ) {
                    Text(text = it, fontSize = 64.sp)
                }
            }
        }
    }
}

data class CalculatorButtonItem(val title: String, val add: String, )