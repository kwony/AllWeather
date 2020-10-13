package kwony.allweather.data.asset

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface AssetDao {
    @Query("select * from assetentity")
    fun getEntities(): Flowable<List<AssetEntity>>
}