package kwony.allweather.editor

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.common.TypeAdapter
import kwony.allweather.common.TypeAdapterItem
import kwony.allweather.data.asset.AssetTypeMeta
import java.lang.IllegalArgumentException

class AssetEditorTypeAdapter(private val listener: AccountAssetTypeAdapterListener) :
    TypeAdapter<AssetTypeViewType, RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AssetTypeViewType.NORMAL.ordinal -> { AssetTypeViewHolder.newInstance(parent) }
            AssetTypeViewType.ADD.ordinal -> { AssetTypeAddViewHolder.newInstance(parent) }
            else -> throw IllegalArgumentException("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AssetTypeViewHolder -> holder.bind(items[position] as AssetTypeAdapterItem, listener)
            is AssetTypeAddViewHolder -> holder.bind(listener)
        }
    }
}

enum class AssetTypeViewType {
    NORMAL, ADD
}

interface AccountAssetTypeAdapterListener {
    fun add()
    fun edit(assetTypeMeta: AssetTypeMeta)
    fun delete(assetTypeMeta: AssetTypeMeta)
}

data class AssetTypeAdapterItem(
    val assetTypeMeta: AssetTypeMeta
): TypeAdapterItem<AssetTypeViewType> {
    override val itemId: Long get() = assetTypeMeta.hashCode().toLong()
    override val viewType: AssetTypeViewType get() = AssetTypeViewType.NORMAL
}

data class AssetTypeAdapterEmptyItem(
    val empty: Boolean = true
): TypeAdapterItem<AssetTypeViewType> {
    override val itemId: Long get() = this.hashCode().toLong()
    override val viewType: AssetTypeViewType get() = AssetTypeViewType.ADD
}