package com.example.architecture.items.domain.use_cases

import com.example.architecture.items.data.apiServices.ItemDetailsDto
import com.example.architecture.items.domain.repository.ItemRepository
import com.example.architecture.items.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemDetailsUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke(id: String): Flow<Resource<ItemDetailsDto>> {
        return repository.getItemDetails(id)
    }
}