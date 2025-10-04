package com.example.driving_car_project.util

import com.example.driving_car_project.model.AnswerOption
import com.example.driving_car_project.model.Question

fun Question.toAnswerOptions(): List<AnswerOption> {
    val list = mutableListOf<AnswerOption>()

    val normalizedAnswer = answer?.trim()?.lowercase() ?: ""

    option.a?.let {
        list.add(AnswerOption("A", it, normalizedAnswer == "a", if (normalizedAnswer == "a") suggest else null))
    }
    option.b?.let {
        list.add(AnswerOption("B", it, normalizedAnswer == "b", if (normalizedAnswer == "b") suggest else null))
    }
    option.c?.let {
        list.add(AnswerOption("C", it, normalizedAnswer == "c", if (normalizedAnswer == "c") suggest else null))
    }
    option.d?.let {
        list.add(AnswerOption("D", it, normalizedAnswer == "d", if (normalizedAnswer == "d") suggest else null))
    }
    option.e?.let {
        list.add(AnswerOption("E", it, normalizedAnswer == "e", if (normalizedAnswer == "e") suggest else null))
    }

    return list
}



