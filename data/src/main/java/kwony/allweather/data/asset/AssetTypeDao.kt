package kwony.allweather.data.asset

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface AssetTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(assetType: AssetTypeMeta): Long

    @Query("select * from AssetTypeMeta")
    fun getEntities(): Flowable<List<AssetTypeMeta>>

    @Query("select * from AssetTypeMeta where assetTypeMeta.accountId = :accountId")
    fun getEntitiesFromAccount(accountId: Long): Flowable<List<AssetTypeMeta>>

    @Query("delete from assettypemeta where assetTypeId = :assetTypeId")
    fun delete(assetTypeId: Long)
}