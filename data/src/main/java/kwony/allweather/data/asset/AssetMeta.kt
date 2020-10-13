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
    val assetType: Int,
    val domesticStockRate: Float = 0f,
    val domesticBondRate: Float = 0f,
    val advancedStockRate: Float = 0f,
    val advancedBondRate: Float = 0f,
    val emergingStockRate: Float = 0f,
    val emergingBondRate: Float = 0f,
    val goldRate: Float = 0f,
    val silverRate: Float = 0f,
    val commodityRate: Float = 0f
) {
    companion object {
        @JvmStatic
        fun createAssetByType(type: AssetType, assetId: Long = 0L, accountId: Long, name: String, amount: Int): AssetMeta {
            return when (type) {
                AssetType.DOMESTIC_STOCK -> {
                    AssetMeta(
                        assetId = assetId,
                        accountId = accountId,
                        assetName = name,
                        assetAmount = amount,
                        assetType = AssetType.DOMESTIC_STOCK.code,
                        domesticStockRate = 1f
                    )
                }
                AssetType.DOMESTIC_BOND -> {
                    AssetMeta(
                        assetId = assetId,
                        accountId = accountId,
                        assetName = name,
                        assetAmount = amount,
                        assetType = AssetType.DOMESTIC_BOND.code,
                        domesticBondRate = 1f
                    )
                }
                AssetType.ADVANCED_STOCK -> {
                    AssetMeta(
                        assetId = assetId,
                        accountId = accountId,
                        assetName = name,
                        assetAmount = amount,
                        assetType = AssetType.ADVANCED_STOCK.code,
                        advancedStockRate = 1f
                    )
                }
                AssetType.ADVANCED_BOND -> {
                    AssetMeta(
                        assetId = 0L,
                        accountId = accountId,
                        assetName = name,
                        assetAmount = amount,
                        assetType = AssetType.ADVANCED_BOND.code,
                        advancedBondRate = 1f
                    )
                }
                AssetType.EMERGING_STOCK -> {
                    AssetMeta(
                        assetId = 0L,
                        accountId = accountId,
                        assetName = name,
                        assetAmount = amount,
                        assetType = AssetType.EMERGING_STOCK.code,
                        emergingStockRate = 1f
                    )
                }
                AssetType.EMERGING_BOND -> {
                    AssetMeta(
                        assetId = 0L,
                        accountId = accountId,
                        assetName = name,
                        assetAmount = amount,
                        assetType = AssetType.EMERGING_BOND.code,
                        emergingBondRate = 1f
                    )
                }
                AssetType.GOLD -> {
                    AssetMeta(
                        assetId = 0L,
                        accountId = accountId,
                        assetName = name,
                        assetAmount = amount,
                        assetType = AssetType.GOLD.code,
                        goldRate = 1f

                    )
                }
                AssetType.SILVER -> {
                    AssetMeta(
                        assetId = 0L,
                        accountId = accountId,
                        assetName = name,
                        assetAmount = amount,
                        assetType = AssetType.SILVER.code,
                        silverRate = 1f
                    )
                }
                AssetType.COMMODITY -> {
                    AssetMeta(
                        assetId = 0L,
                        accountId = accountId,
                        assetName = name,
                        assetAmount = amount,
                        assetType = AssetType.COMMODITY.code,
                        commodityRate = 1f
                    )
                }
                else -> {
                    throw IllegalArgumentException("unable to handle asset type $type")
                }
            }
        }

        @JvmStatic
        fun createMixedAsset(
            assetId: Long = 0L,
            accountId: Long,
            name: String,
            amount: Int,
            domesticStockRate: Float,
            domesticBondRate: Float,
            advancedStockRate: Float,
            advancedBondRate: Float,
            emergingStockRate: Float,
            emergingBondRate: Float,
            goldRate: Float,
            silverRate: Float,
            commodityRate: Float
        ): AssetMeta {
            require(domesticStockRate + domesticBondRate + advancedStockRate + advancedBondRate + emergingStockRate + emergingBondRate + goldRate + silverRate + commodityRate <= 1f)

            return AssetMeta(
                assetId = assetId,
                accountId = accountId,
                assetName = name,
                assetAmount = amount,
                assetType = AssetType.MIXED.code,
                domesticStockRate = domesticStockRate,
                domesticBondRate = domesticBondRate,
                advancedStockRate = advancedStockRate,
                advancedBondRate = advancedBondRate,
                emergingStockRate = emergingStockRate,
                emergingBondRate = emergingBondRate,
                goldRate = goldRate,
                silverRate = silverRate,
                commodityRate = commodityRate
            )
        }
    }
}