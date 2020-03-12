package com.tadiuzzz.baseapp.feature.example

import com.tadiuzzz.baseapp.data.repository.EntityRepository
import javax.inject.Inject

class GetDataFromDbUseCase @Inject constructor(private val repository: EntityRepository) {
    suspend operator fun invoke(id: Int) = repository.getDataFromDb(id)
}