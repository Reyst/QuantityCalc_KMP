package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import domain.CalcType
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import quantitycalc.composeapp.generated.resources.Res
import quantitycalc.composeapp.generated.resources.app_name
import quantitycalc.composeapp.generated.resources.*
//import androidx.compose.material3.MaterialTheme as MT3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var calcType: CalcType by rememberSaveable { mutableStateOf(CalcType.Income) }

        Scaffold(
            topBar = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    CenterAlignedTopAppBar(
                        title = { Text(text = stringResource(Res.string.app_name)) }
                    )
                    TabRow(selectedTabIndex = calcType.ordinal) {
                        Tab(
                            selected = calcType == CalcType.Income,
                            onClick = { calcType = CalcType.Income },
                            text = { Text(text = stringResource(Res.string.income)) },
                        )
                        Tab(
                            selected = calcType == CalcType.Outcome,
                            onClick = { calcType = CalcType.Outcome },
                            text = { Text(text = stringResource(Res.string.outcome)) },
                        )
                    }
                }
            }
        ) { CalculatorScreen(calcType, Modifier.padding(it)) }
    }
}

