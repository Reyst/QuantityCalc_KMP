package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.CalculationResult
import domain.model.isValid
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import quantitycalc.composeapp.generated.resources.Res
import quantitycalc.composeapp.generated.resources.absolute_colon
import quantitycalc.composeapp.generated.resources.income_colon
import quantitycalc.composeapp.generated.resources.outcome_colon
import quantitycalc.composeapp.generated.resources.relative_colon
import quantitycalc.composeapp.generated.resources.result
import quantitycalc.composeapp.generated.resources.value_is_unreachable
import utils.DecimalFormatter
import utils.fixedFractionDigits

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
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(12.dp)
                )
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

@Composable
@Preview
private fun ResultOutputPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ResultOutput(CalculationResult())

        ResultOutput(CalculationResult(incomeValue = Double.NaN))
    }
}

