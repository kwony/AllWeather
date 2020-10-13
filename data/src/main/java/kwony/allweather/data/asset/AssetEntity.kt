package kwony.allweather.data.asset

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AssetEntity (@PrimaryKey(autoGenerate = true) val id: Long)