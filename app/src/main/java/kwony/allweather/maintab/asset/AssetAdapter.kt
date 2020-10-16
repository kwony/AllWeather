package kwony.allweather.maintab.asset

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.common.SimpleAdapter
import kwony.allweather.data.asset.AssetMeta

class AssetAdapter(private val listener: AssetAdapterClickListener?) : SimpleAdapter<AssetMeta, RecyclerView.ViewHolder>() {
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