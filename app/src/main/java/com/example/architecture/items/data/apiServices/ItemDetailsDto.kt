package com.example.architecture.items.data.apiServices

import com.squareup.moshi.Json

data class ItemDetailsDto(
    val id: Int,
    @field:Json(name = "title")
    val itemName: String
)
