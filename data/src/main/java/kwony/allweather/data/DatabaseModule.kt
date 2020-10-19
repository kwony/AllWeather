package kwony.allweather.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kwony.allweather.data.account.AccountRepository
import kwony.allweather.data.asset.AssetRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .build()
    }

//    @Singleton
//    @Provides
//    fun providesAccountRepository(appDatabase: AppDatabase) = AccountRepository(appDatabase.accountDao())
//
//    @Singleton
//    @Provides
//    fun provideAssetRepository(appDatabase: AppDatabase) = AssetRepository(appDatabase.assetDao())

}