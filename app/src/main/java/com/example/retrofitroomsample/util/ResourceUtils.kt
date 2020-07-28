package com.example.retrofitroomsample.util

import java.util.*

@ExperimentalStdlibApi
fun String.capitalizeWords() =
    split(" ").joinToString(" ") { it.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT) }