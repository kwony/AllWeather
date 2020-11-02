package kwony.allweather.maintab.detail


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.common.SimpleAdapter
import kwony.allweather.maintab.AssetTypeItem
import kwony.allweather.utils.DimensionUtils

class DetailAdapter(private val listener: DetailAdapterListener): SimpleAdapter<AssetTypeItem, RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DetailViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DetailViewHolder).bind(items[position], listener = listener)

        if (position == itemCount - 1) {
            (holder.itemView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                bottomMargin = DimensionUtils.dp2px(holder.itemView.context, 6f).toInt()
            }
        } else {
            (holder.itemView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                bottomMargin = DimensionUtils.dp2px(holder.itemView.context, 0f).toInt()
            }
        }
    }
}

interface DetailAdapterListener {
    fun clickEdit(item: AssetTypeItem) {

    }

    fun clickDelete(item: AssetTypeItem) {

    }
}