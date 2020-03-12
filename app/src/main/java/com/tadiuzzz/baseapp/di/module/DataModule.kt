package com.tadiuzzz.baseapp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import com.tadiuzzz.baseapp.data.source.local.AppDatabase
import com.tadiuzzz.baseapp.data.source.local.dao.EntityExampleDao
import com.tadiuzzz.baseapp.di.scope.AppScope

@Module
class DataModule{
    @AppScope
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java,"base_db")
//            .addMigrations(Database.MIGRATION_1_2, Database.MIGRATION_2_3) добавление миграции
            .build()
    }

    @Provides
    fun provideEntityExampleDao(db: AppDatabase): EntityExampleDao {
        return db.getEntityExampleDao()
    }


}
