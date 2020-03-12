package com.tadiuzzz.baseapp.data.source.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * Родительский интерфейс с базовыми дженерик методами для работы с таблицами Room
 *
 * @param T класс объекта Entity
 */
interface EntityDao<T> {
    @Insert
    suspend fun insert(entity: T)

    @Insert
    suspend fun insert(entity: List<T>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: List<T>?)

    @Delete
    suspend fun delete(entity: List<T>?)

    @Delete
    suspend fun delete(entity: T)
}
