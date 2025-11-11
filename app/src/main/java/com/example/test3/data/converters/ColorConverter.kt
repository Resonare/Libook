package com.example.test3.data.converters

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter

class ColorConverters {
    @TypeConverter
    fun colorToInt(color: Color?): Int? {
        if (color == null) return null
        val a = (color.alpha * 255f).toInt() and 0xFF
        val r = (color.red   * 255f).toInt() and 0xFF
        val g = (color.green * 255f).toInt() and 0xFF
        val b = (color.blue  * 255f).toInt() and 0xFF
        return (a shl 24) or (r shl 16) or (g shl 8) or b
    }

    @TypeConverter
    fun intToColor(value: Int?): Color? {
        if (value == null) return null
        val a = (value shr 24) and 0xFF
        val r = (value shr 16) and 0xFF
        val g = (value shr 8) and 0xFF
        val b = value and 0xFF
        return Color(r / 255f, g / 255f, b / 255f, a / 255f)
    }
}