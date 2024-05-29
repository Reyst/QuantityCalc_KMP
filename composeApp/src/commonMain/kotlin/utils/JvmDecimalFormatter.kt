package utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

private class JvmDecimalFormatter(
    private val numberFormat: NumberFormat,
): DecimalFormatter {

    override var minimumFractionDigits: Int
        get() = numberFormat.minimumFractionDigits
        set(value) { numberFormat.minimumFractionDigits = value }
    override var maximumFractionDigits: Int
        get() = numberFormat.maximumFractionDigits
        set(value) { numberFormat.maximumFractionDigits = value }

    override fun format(value: Double): String = numberFormat.format(value)
}

fun getDecimalFormatterInstance(): DecimalFormatter {
    return Locale.getDefault()
        .let(DecimalFormat::getInstance)
        .let(::JvmDecimalFormatter)
}
