package com.example.architecture.items.domain.models

data class ItemDetailsModel (
    val id: Int,
    val title: String,
    val description: String,
    val images: List<String>
)
