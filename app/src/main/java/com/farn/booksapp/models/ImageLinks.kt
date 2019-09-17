package com.farn.booksapp.models

import java.io.Serializable

data class ImageLinks(
    val smallThumbnail : String,
    var thumbnail : String
) : Serializable