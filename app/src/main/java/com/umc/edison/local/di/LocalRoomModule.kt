package com.umc.edison.local.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.umc.edison.local.room.EdisonDatabase
import com.umc.edison.local.room.RoomConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LocalRoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): EdisonDatabase = Room.databaseBuilder(
        context,
        EdisonDatabase::class.java,
        RoomConstant.ROOM_DB_NAME
    )
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                copyDatabaseFromAssets(context)
            }
        })
        .build()

    @Provides
    @Singleton
    fun provideBubbleDao(database: EdisonDatabase) = database.bubbleDao()

    @Provides
    @Singleton
    fun provideLabelDao(database: EdisonDatabase) = database.labelDao()
}

private fun copyDatabaseFromAssets(context: Context) {
    val databaseName = RoomConstant.ROOM_DB_NAME
    val dbPath = context.getDatabasePath(databaseName)
    if (!dbPath.exists()) {
        context.assets.open(databaseName).use { inputStream ->
            dbPath.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}