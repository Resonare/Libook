package com.example.test3.other

import androidx.room.TypeConverter
import com.example.test3.data.entities.RateType

class RateTypeConverter {
    @TypeConverter
    fun fromRateType(type: RateType): String = type.name

    @TypeConverter
    fun toRateType(value: String): RateType = RateType.valueOf(value)
}