package kwony.allweather.data.asset

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface AssetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(asset: AssetMeta): Long

    @Query("select * from assetmeta")
    fun getEntities(): Flowable<List<AssetMeta>>

    @Query("select * from assetmeta where assetMeta.accountId = :accountId")
    fun getEntityFromAccount(accountId: Long): Flowable<List<AssetMeta>>


}