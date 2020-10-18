package kwony.allweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import kwony.allweather.data.account.AccountDao
import kwony.allweather.data.account.AccountMeta
import kwony.allweather.data.asset.AssetDao
import kwony.allweather.data.asset.AssetMeta
import kwony.allweather.data.asset.AssetTypeDao
import kwony.allweather.data.asset.AssetTypeMeta

@Database(entities = [AssetMeta::class, AssetTypeMeta::class, AccountMeta::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun assetDao(): AssetDao

    abstract fun assetTypeDao(): AssetTypeDao

    abstract fun accountDao(): AccountDao
}