package com.example.driving_car_project.util.converter

import androidx.room.TypeConverter
import com.example.driving_car_project.data.model.Image
import com.example.driving_car_project.data.model.Option
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

object Converter {
    //Convert Option <-> JSON
    @TypeConverter
    @JvmStatic
    fun fromOption(option: Option?): String? =
        option?.let { Gson().toJson(option) }

    @TypeConverter
    @JvmStatic
    fun toOption(json: String?): Option? =
        json?.let { Gson().fromJson(json, Option::class.java) }

    //Convert Image <-> JSON
    @TypeConverter
    @JvmStatic
    fun fromImage(image: Image?): String? =
        image?.let { Gson().toJson(it) }

    @TypeConverter
    @JvmStatic
    fun toImage(json: String?): Image? =
        json?.let { Gson().fromJson(it, Image::class.java) }

    //Convert List<Int> <-> String
    @TypeConverter
    @JvmStatic
    fun fromList(list: List<Int>?): String? =
        list?.let { Gson().toJson(it) }

    @TypeConverter
    @JvmStatic
    fun toList(json: String?): List<Int> {
        return if(json.isNullOrEmpty()) emptyList()
        else Gson().fromJson(json, object : TypeToken<List<Int>>() {}.type)
    }

    //Convert Date <-> Long
    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    @JvmStatic
    fun toDate(millis: Long?): Date? = millis?.let { Date(it) }
}