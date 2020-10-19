package kwony.allweather.data.account

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountMeta(
    @PrimaryKey(autoGenerate = true) val accountId: Long,
    val accountName: String,
    val isDefault: Boolean
)