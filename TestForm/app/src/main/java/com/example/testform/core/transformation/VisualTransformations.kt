package com.example.testform.core.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Máscara para Telefone: (99) 99999-9999
 */
class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(11)
        val out = StringBuilder()
        for (i in trimmed.indices) {
            if (i == 0) out.append("(")
            out.append(trimmed[i])
            if (i == 1) out.append(") ")
            if (i == 6) out.append("-")
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val realOffset = offset.coerceAtMost(trimmed.length)
                var transformedOffset = realOffset
                if (realOffset > 0) transformedOffset += 1
                if (realOffset > 1) transformedOffset += 2
                if (realOffset > 6) transformedOffset += 1
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                val realOffset = offset.coerceAtMost(out.length)
                var originalOffset = realOffset
                if (realOffset > 10) originalOffset -= 4
                else if (realOffset > 4) originalOffset -= 3
                else if (realOffset > 0) originalOffset -= 1
                return originalOffset.coerceIn(0, text.text.length)
            }
        }

        return TransformedText(AnnotatedString(out.toString()), offsetMapping)
    }
}

/**
 * Máscara para CPF: 999.999.999-99
 */
class CpfVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(11)
        val out = StringBuilder()
        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if (i == 2 || i == 5) out.append(".")
            if (i == 8) out.append("-")
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val realOffset = offset.coerceAtMost(trimmed.length)
                var transformedOffset = realOffset
                if (realOffset > 2) transformedOffset += 1
                if (realOffset > 5) transformedOffset += 1
                if (realOffset > 8) transformedOffset += 1
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                val realOffset = offset.coerceAtMost(out.length)
                var originalOffset = realOffset
                if (realOffset > 11) originalOffset -= 3
                else if (realOffset > 7) originalOffset -= 2
                else if (realOffset > 3) originalOffset -= 1
                return originalOffset.coerceIn(0, text.text.length)
            }
        }

        return TransformedText(AnnotatedString(out.toString()), offsetMapping)
    }
}

/**
 * Máscara para Data de Nascimento: 99/99/9999
 */
class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(8)
        val out = StringBuilder()
        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if (i == 1 || i == 3) out.append("/")
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val realOffset = offset.coerceAtMost(trimmed.length)
                var transformedOffset = realOffset
                if (realOffset > 1) transformedOffset += 1
                if (realOffset > 3) transformedOffset += 1
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                val realOffset = offset.coerceAtMost(out.length)
                var originalOffset = realOffset
                if (realOffset > 5) originalOffset -= 2
                else if (realOffset > 2) originalOffset -= 1
                return originalOffset.coerceIn(0, text.text.length)
            }
        }

        return TransformedText(AnnotatedString(out.toString()), offsetMapping)
    }
}
