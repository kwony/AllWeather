package kwony.allweather.editor

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.common.TypeAdapter
import kwony.allweather.common.TypeAdapterItem
import kwony.allweather.data.asset.AssetTypeMeta

class AccountEditorTypeAdapter: TypeAdapter<AssetTypeViewType, RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            AssetTypeViewType.NORMAL.ordinal -> {

            }
            AssetTypeViewType.ADD.ordinal -> {

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }
}

enum class AssetTypeViewType {
    NORMAL, ADD
}

data class AssetTypeAdapterItem(
    val assetTypeMeta: AssetTypeMeta
): TypeAdapterItem<AssetTypeViewType> {
    override val itemId: Long
        get() = assetTypeMeta.hashCode().toLong()
    override val viewType: AssetTypeViewType
        get() = AssetTypeViewType.NORMAL
}

data class AssetTypeAdapterEmptyItem(
    val empty: Boolean
): TypeAdapterItem<AssetTypeViewType> {
    override val itemId: Long
        get() = this.hashCode().toLong()
    override val viewType: AssetTypeViewType
        get() = AssetTypeViewType.ADD
}