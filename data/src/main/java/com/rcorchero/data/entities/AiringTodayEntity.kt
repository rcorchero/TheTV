package com.rcorchero.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AiringTodayEntity(
    @Json(name = "page") val page: Int = 1,
    @Json(name = "total_results") val totalResults: Int = 0,
    @Json(name = "total_pages") val totalPages: Int = 1,
    @Json(name = "results") val results: List<TVShowEntity> = listOf()
)