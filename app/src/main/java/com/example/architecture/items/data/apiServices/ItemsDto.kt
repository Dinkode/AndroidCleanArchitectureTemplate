package com.example.architecture.items.data.apiServices

import com.squareup.moshi.Json

data class ItemsDto(
    @field:Json(name = "products")
    val items: List<ItemDataDto>
)