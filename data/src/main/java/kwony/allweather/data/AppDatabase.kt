package kwony.allweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import kwony.allweather.data.account.AccountDao
import kwony.allweather.data.account.AccountMeta
import kwony.allweather.data.asset.AssetDao
import kwony.allweather.data.asset.AssetMeta

@Database(entities = [AssetMeta::class, AccountMeta::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun assetDao(): AssetDao

    abstract fun accountDao(): AccountDao
}