package com.example.architecture.items.domain.use_cases

import com.example.architecture.items.data.apiServices.ItemsDto
import com.example.architecture.items.domain.repository.ItemRepository
import com.example.architecture.items.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke(): Flow<Resource<ItemsDto>> {
        return repository.getItems()
    }
}