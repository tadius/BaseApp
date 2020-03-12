package com.tadiuzzz.baseapp.feature.example

import com.tadiuzzz.baseapp.data.repository.EntityRepository
import javax.inject.Inject

class GetDataUseCase @Inject constructor(private val repository: EntityRepository) {
    suspend operator fun invoke(id: String, amount: String) = repository.getData(id, amount)
}