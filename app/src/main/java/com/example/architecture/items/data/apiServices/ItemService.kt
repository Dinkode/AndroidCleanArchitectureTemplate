package com.example.architecture.items.data.apiServices

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemService {

    @GET("products")
    suspend fun getItems(
    ): Response<ItemsDto>

    @GET("products/{id}")
    suspend fun getItemDetails(
        @Path("id") id: String
    ): Response<ItemDetailsDto>
}