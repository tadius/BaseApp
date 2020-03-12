package com.tadiuzzz.baseapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tadiuzzz.baseapp.data.entity.EntityExample
import com.tadiuzzz.baseapp.data.source.local.dao.EntityExampleDao

@Database (entities = [EntityExample::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        /**
         * Объект миграции базы данных.
         * При обновлении данных необходимо повысить версию базы,
         * создать новый объект миграции, добавить миграцию в DataModule
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                /* пример запроса миграции
                database.execSQL("ALTER TABLE Employee ADD COLUMN birthday INTEGER DEFAULT 0 NOT NULL")
                 */
            }
        }
    }

    abstract fun getEntityExampleDao(): EntityExampleDao

}