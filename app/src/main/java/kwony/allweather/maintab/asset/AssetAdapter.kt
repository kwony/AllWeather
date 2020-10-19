package kwony.allweather.maintab.asset

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.common.SimpleAdapter
import kwony.allweather.data.asset.AssetMeta
import kwony.allweather.data.asset.AssetTypeMeta

class AssetAdapter(private val listener: AssetAdapterClickListener?) : SimpleAdapter<AssetAdapterItem, RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AssetDefaultViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AssetDefaultViewHolder).bind(items[position], listener)
    }
}

interface AssetAdapterClickListener {
    fun editClick(assetMeta: AssetMeta)
    fun deleteClick(assetMeta: AssetMeta)
}

data class AssetAdapterItem(val assetMeta: AssetMeta, val assetTypeMeta: AssetTypeMeta?) {
    val assetName get() = assetMeta.assetName

    val assetAmount get() = assetMeta.assetAmount

    val assetTypeName get() = assetTypeMeta?.assetTypeName

    val assetTypeId get() = assetTypeMeta?.assetTypeId
}