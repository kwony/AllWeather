package kwony.allweather.data.asset

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class AssetTypeMeta(
    @PrimaryKey(autoGenerate = true) val assetTypeId: Long,
    var accountId: Long,
    var assetTypeName: String,
    var targetWeight: Int
): Parcelable