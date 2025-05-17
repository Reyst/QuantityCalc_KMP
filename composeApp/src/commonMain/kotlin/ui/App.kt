package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import domain.CalcType
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import quantitycalc.composeapp.generated.resources.Res
import quantitycalc.composeapp.generated.resources.app_name
import quantitycalc.composeapp.generated.resources.income
import quantitycalc.composeapp.generated.resources.outcome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var calcType: CalcType by rememberSaveable { mutableStateOf(CalcType.Income) }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars) // Встановлює висоту рівну висоті рядка стану
                    .background(MaterialTheme.colorScheme.primary)
            )

            Scaffold(
                topBar = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CenterAlignedTopAppBar(
                            title = { Text(text = stringResource(Res.string.app_name)) },
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
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            ) { CalculatorScreen(calcType, Modifier.padding(it)) }
        }
    }
}
