package utils

interface DecimalFormatter {

    var minimumFractionDigits: Int
    var maximumFractionDigits: Int

    fun format(value: Double): String

    companion object {
        fun getInstance(): DecimalFormatter = getDecimalFormatterInstance()
    }
}

var DecimalFormatter.fixedFractionDigits: Int
    get() = maxOf(minimumFractionDigits, maximumFractionDigits)
    set(value) {
        minimumFractionDigits = value
        maximumFractionDigits = value
    }

//expect fun getDecimalFormatterInstance(): DecimalFormatter
