package com.example.remidipam.di

import android.content.Context
import androidx.room.Room
import com.example.remidipam.entity.AppDatabase
import com.example.remidipam.dao.LibraryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "library_manager.db"
        )
            .fallbackToDestructiveMigration() // Tambahan agar tidak crash jika ubah struktur DB
            .build()
    }

    // [PENTING] Kita sediakan DAO agar Repository bisa menggunakannya otomatis
    @Provides
    @Singleton
    fun provideLibraryDao(db: AppDatabase): LibraryDao {
        return db.libraryDao()
    }

    // CATATAN: Fungsi provideRepository DIHAPUS saja.
    // Karena LibraryRepository sudah punya @Inject constructor, Hilt akan otomatis
    // membuatnya menggunakan provideDatabase dan provideLibraryDao di atas.
}