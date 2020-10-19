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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(assetType: List<AssetTypeMeta>): List<Long>

    @Query("select * from assettypemeta where assetTypeId = :assetTypeId")
    fun getEntity(assetTypeId: Long): Flowable<AssetTypeMeta>

    @Query("select * from AssetTypeMeta")
    fun getEntities(): Flowable<List<AssetTypeMeta>>

    @Query("select * from AssetTypeMeta where assetTypeMeta.accountId = :accountId")
    fun getEntitiesFromAccount(accountId: Long): Flowable<List<AssetTypeMeta>>

    @Query("delete from assettypemeta where assetTypeId = :assetTypeId")
    fun delete(assetTypeId: Long)

    @Query("delete from assettypemeta where accountId = :accountId")
    fun deleteByAccountId(accountId: Long)
}