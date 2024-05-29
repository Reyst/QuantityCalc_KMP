package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import domain.CalcType
import domain.QuantityCalc
import domain.model.CalculationResult
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import quantitycalc.composeapp.generated.resources.Res
import quantitycalc.composeapp.generated.resources.absolute_loss
import quantitycalc.composeapp.generated.resources.arrow_down_24
import quantitycalc.composeapp.generated.resources.arrow_up_24
import quantitycalc.composeapp.generated.resources.desired_amount
import quantitycalc.composeapp.generated.resources.income
import quantitycalc.composeapp.generated.resources.percent_of_loss
import quantitycalc.composeapp.generated.resources.percent_sign
import quantitycalc.composeapp.generated.resources.precision

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