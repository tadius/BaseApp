package com.tadiuzzz.baseapp.data.repository

import retrofit2.Response
import com.tadiuzzz.baseapp.data.entity.EntityExample
import com.tadiuzzz.baseapp.data.source.local.dao.EntityExampleDao
import com.tadiuzzz.baseapp.data.source.remote.BaseApi
import com.tadiuzzz.baseapp.di.scope.AppScope
import javax.inject.Inject

@AppScope
class EntityRepository @Inject constructor(private val baseApi: BaseApi, private val entityExampleDao: EntityExampleDao) {

    suspend fun getData(id: String, amount: String): Response<EntityExample> {
        return baseApi.getData(id, amount)
    }

    suspend fun getDataFromDb(id: Int) : List<EntityExample>? {
        return entityExampleDao.getObjectById(id)
    }

}