package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import domain.CalcType
import domain.QuantityCalc
import domain.model.CalculationResult
import domain.model.isValid
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import quantitycalc.composeapp.generated.resources.Res
import quantitycalc.composeapp.generated.resources.app_name
import quantitycalc.composeapp.generated.resources.*
//import androidx.compose.material3.MaterialTheme as MT3
import utils.DecimalFormatter
import utils.fixedFractionDigits

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

@Composable
fun CalculatorScreen(
    calcType: CalcType,
    modifier: Modifier = Modifier,
//    onTabSelected: (CalcType) -> Unit,
//    onParamsChangeListener: () -> Unit,
) {

//    val decimalInputFormatter = remember { DecimalInputFormatter() }
//    val numberTransformation = remember { DecimalInputVisualTransformation(decimalInputFormatter) }

    val calc: QuantityCalc = remember(calcType) { QuantityCalc.getCalcByType(calcType) }

    var baseValue by rememberSaveable { mutableStateOf("") }
    var percentValue by rememberSaveable { mutableStateOf("") }
    var absoluteValue by rememberSaveable { mutableStateOf("") }
    var precision by rememberSaveable { mutableIntStateOf(3) }

    val calculationResult: CalculationResult by remember(calcType) {
        derivedStateOf {
            calc.calc(
                baseValue = baseValue.toDoubleOrNull() ?: 0.0,
                percentLosses = percentValue.toDoubleOrNull() ?: 0.0,
                absoluteLosses = absoluteValue.toDoubleOrNull() ?: 0.0,
            )
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(12.dp)
        ) {


            val labelText = stringResource(
                if (calcType == CalcType.Income) Res.string.desired_amount
                else Res.string.income
            )

            OutlinedTextField(
                value = baseValue,
                onValueChange = { baseValue = it /*decimalInputFormatter.cleanup(it)*/ },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = { Text(text = labelText) },
                singleLine = true,
//                visualTransformation = numberTransformation,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = percentValue,
                onValueChange = { percentValue = it /*decimalInputFormatter.cleanup(it)*/ },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = { Text(text = stringResource(Res.string.percent_of_loss)) },
                prefix = { Text(text = stringResource(Res.string.percent_sign)) },
                singleLine = true,
//                visualTransformation = numberTransformation,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = absoluteValue,
                onValueChange = { absoluteValue = it /*decimalInputFormatter.cleanup(it)*/ },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                label = { Text(stringResource(Res.string.absolute_loss)) },
                singleLine = true,
//                visualTransformation = numberTransformation,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precision.toString(),
                onValueChange = { /*precision = decimalInputFormatter.cleanup(it)*/ },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(stringResource(Res.string.precision)) },
                singleLine = true,
                trailingIcon = {
                    Row {
                        IconButton(
                            onClick = { precision = (precision + 1).coerceAtMost(5) }
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_up_24),
                                contentDescription = null,
                            )
                        }
                        IconButton(
                            onClick = { precision = (precision - 1).coerceAtLeast(0) }
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_down_24),
                                contentDescription = null,
                            )
                        }
                    }
                },
//                visualTransformation = numberTransformation,

//                modifier = Modifier.fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .weight(1F)
//                .padding(12.dp)
                .fillMaxWidth(),
        ) {
            ResultOutput(
                result = calculationResult,
                precision = precision,
            )
        }
    }
}

@Composable
fun ResultOutput(result: CalculationResult, precision: Int = 3) {

    val formatter = remember { DecimalFormatter.getInstance() }
        .apply { fixedFractionDigits = precision }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {

        val isResultValid = result.isValid()

        val titleId =
            if (isResultValid) Res.string.result
            else Res.string.value_is_unreachable

        val titleColor =
            if (isResultValid) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.error

        Text(
            text = stringResource(titleId),
            color = titleColor,
            style = MaterialTheme.typography.headlineSmall,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .background(Color.LightGray)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .wrapContentHeight()
                .padding(12.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(Res.string.income_colon),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = formatter.format(result.incomeValue))
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(Res.string.relative_colon),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = formatter.format(result.relativeLosses))
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(Res.string.absolute_colon),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = formatter.format(result.absoluteLosses))
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(Res.string.outcome_colon),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = formatter.format(result.outcomeValue))
            }
        }
    }
}
