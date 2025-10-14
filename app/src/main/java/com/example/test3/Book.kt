package com.example.test3

import java.io.Serializable

data class Book (
    var title: String,
    var author: String,
    var worldRate: Int?,
    var coverId: Int,
    val myThoughts: MutableList<MyThought>,
    val rates: MutableList<Rate>
): Serializable