package domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class CalculationResult(
    val incomeValue: Double = 0.0,
    val relativeLosses: Double = 0.0,
    val absoluteLosses:Double = 0.0,
    val outcomeValue: Double = 0.0,
)

fun CalculationResult.isValid() = listOf(incomeValue, relativeLosses, absoluteLosses, outcomeValue)
    .all { !it.isNaN() && !it.isInfinite() }
