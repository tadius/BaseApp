package com.tadiuzzz.baseapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.tadiuzzz.baseapp.data.entity.EntityExample
import com.tadiuzzz.baseapp.data.source.local.EntityDao

@Dao
interface EntityExampleDao :
    EntityDao<EntityExample> {

    @Query("SELECT * FROM entityexample WHERE id = :id")
    suspend fun getObjectById(id: Int): List<EntityExample>?

}