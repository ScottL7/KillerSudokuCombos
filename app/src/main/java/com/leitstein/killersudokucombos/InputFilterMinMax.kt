package com.leitstein.killersudokucombos

import android.text.InputFilter
import android.text.Spanned
import java.lang.NumberFormatException

// Ref: https://stackoverflow.com/questions/53758285/how-to-set-input-type-and-format-in-edittext-using-kotlin

class InputFilterMinMax(private var min: Int, private var max: Int) : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
                val input = (dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length)).toInt()
                if (isInRange(min, max, input)) {
                    return null
            }
        } catch (nfe: NumberFormatException) {}
        return ""
    }

    private fun isInRange(a:Int, b:Int, c:Int):Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}