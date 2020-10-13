package kwony.allweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import kwony.allweather.data.asset.AssetDao
import kwony.allweather.data.asset.AssetEntity

@Database(entities = [AssetEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun assetDao(): AssetDao
}