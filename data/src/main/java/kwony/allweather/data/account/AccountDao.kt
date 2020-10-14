package kwony.allweather.data.account

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(account: AccountMeta): Long

    @Query("select * from accountmeta")
    fun getEntities(): Flowable<List<AccountMeta>>

    @Query("delete from accountmeta where accountId = :accountId")
    fun delete(accountId: Long)

    @Query("delete from accountmeta")
    fun deleteAll()
}