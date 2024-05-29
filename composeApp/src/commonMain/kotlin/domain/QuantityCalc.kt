package domain

import domain.model.CalculationResult

sealed interface QuantityCalc {
    fun calc(
        baseValue: Double,
        percentLosses: Double = 0.0,
        absoluteLosses: Double = 0.0,
    ): CalculationResult

    companion object {
        fun getCalcByType(type: CalcType): QuantityCalc =
            when(type) {
                CalcType.Income -> IncomeCalc
                CalcType.Outcome -> OutcomeCalc
            }
    }
}

private data object IncomeCalc : QuantityCalc {
    override fun calc(
        baseValue: Double,
        percentLosses: Double,
        absoluteLosses: Double,
    ): CalculationResult {

        val result = (baseValue + absoluteLosses) / (1 - percentLosses / 100)

        return CalculationResult(
            result,
            percentLosses * result / 100,
            absoluteLosses,
            baseValue,
        )
    }
}

private data object OutcomeCalc : QuantityCalc {
    override fun calc(
        baseValue: Double,
        percentLosses: Double,
        absoluteLosses: Double,
    ): CalculationResult {

        val relativeLosses = baseValue * percentLosses / 100

        return CalculationResult(
            baseValue,
            relativeLosses,
            absoluteLosses,
            baseValue - relativeLosses - absoluteLosses,
        )
    }
}