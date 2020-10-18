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

    @Query("select * from AssetMeta where assetId = :assetId")
    fun getEntity(assetId: Long): Flowable<AssetMeta>

    @Query("select * from assetmeta")
    fun getEntities(): Flowable<List<AssetMeta>>

    @Query("select * from assetmeta where assetMeta.accountId = :accountId")
    fun getEntitiesFromAccount(accountId: Long): Flowable<List<AssetMeta>>

    @Query("delete from assetmeta where assetId = :assetId")
    fun delete(assetId: Long)

    @Query("delete from assetmeta")
    fun deleteAll()
}