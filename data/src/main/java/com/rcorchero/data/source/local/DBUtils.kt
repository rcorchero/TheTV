package com.rcorchero.data.source.local


private const val SEPARATOR = ","

fun List<*>.listToString(): String = this.joinToString(SEPARATOR)

fun String.stringToList(): List<*> = this.split(SEPARATOR)