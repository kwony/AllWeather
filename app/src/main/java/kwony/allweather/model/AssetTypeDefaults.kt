package kwony.allweather.model

import android.content.Context
import kwony.allweather.R
import kwony.allweather.data.asset.AssetMeta
import kwony.allweather.data.asset.AssetTypeMeta

object AssetTypeDefaults {
    fun createDefaultAssetTypes(context: Context): List<AssetTypeMeta> {
        return listOf(
            AssetTypeMeta(
                accountId = 0L,
                assetTypeId = 0L,
                assetTypeName = context.getString(R.string.type_stock),
                targetWeight = 30
            ),
            AssetTypeMeta(
                accountId = 0L,
                assetTypeId = 0L,
                assetTypeName = context.getString(R.string.type_short_bond),
                targetWeight = 15
            ),
            AssetTypeMeta(
                accountId = 0L,
                assetTypeId = 0L,
                assetTypeName = context.getString(R.string.type_long_bond),
                targetWeight = 40
            ),
            AssetTypeMeta(
                accountId = 0L,
                assetTypeId = 0L,
                assetTypeName = context.getString(R.string.type_gold),
                targetWeight = 10
            ),
            AssetTypeMeta(
                accountId = 0L,
                assetTypeId = 0L,
                assetTypeName = context.getString(R.string.type_commodity),
                targetWeight = 5
            )
        )
    }
}

