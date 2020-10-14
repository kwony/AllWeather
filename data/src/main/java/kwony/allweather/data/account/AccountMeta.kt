package kwony.allweather.data.account

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountMeta(
    @PrimaryKey(autoGenerate = true) val accountId: Long,
    val accountName: String,
    val domesticStockRateGoal: Float = 0.1f,
    val domesticBondRateGoal: Float = 0.15f,
    val advancedStockRateGoal: Float = 0.2f,
    val advancedBondRateGoal: Float = 0.2f,
    val emergingStockRateGoal: Float = 0.1f,
    val emergingBondRateGoal: Float = 0.05f,
    val goldRateGoal: Float = 0.1f,
    val silverRateGoal: Float = 0.05f,
    val commodityRateGoal: Float = 0.05f
)