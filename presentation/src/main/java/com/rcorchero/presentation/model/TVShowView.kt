package com.rcorchero.presentation.model

import com.rcorchero.domain.model.TVShow

data class TVShowView(
    val id: Int,
    val name: String,
    val voteAverage: String,
    val posterUrl: String
)

fun TVShow.toViewModel() = TVShowView(
    id = id,
    name = name,
    voteAverage = if (voteAverage <= 0.0) "" else voteAverage.toString(),
    posterUrl = posterUrl
)