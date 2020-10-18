package kwony.allweather.data.asset

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.IllegalArgumentException

@Entity
data class AssetMeta(
    @PrimaryKey(autoGenerate = true) val assetId: Long,
    val accountId: Long,
    val assetName: String,
    val assetAmount: Int,
    val assetTypeId: Long
) {

}