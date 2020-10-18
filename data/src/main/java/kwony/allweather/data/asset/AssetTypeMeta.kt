package kwony.allweather.data.asset

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AssetTypeMeta(
    @PrimaryKey(autoGenerate = true) val assetTypeId: Long,
    val accountId: Long,
    val assetTypeName: String,
    val targetWeight: Float
)