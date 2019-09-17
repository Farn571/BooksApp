package com.farn.booksapp.models

import java.io.Serializable

data class Volume(
    val title : String,
    var imageLinks : ImageLinks,
    val publishedDate : String,
    val description : String
) : Serializable