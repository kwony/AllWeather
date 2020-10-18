package kwony.allweather.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.R
import kwony.allweather.databinding.ViewholderAssetTypeAddBinding

class AssetTypeAddViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ViewholderAssetTypeAddBinding

    companion object {
        @JvmStatic
        fun newInstance(parent: ViewGroup): AssetTypeAddViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_asset_type_add, parent, false)
            return AssetTypeAddViewHolder(view)
        }
    }

    fun bind(listener: AccountAssetTypeAdapterListener) {
        binding = ViewholderAssetTypeAddBinding.bind(itemView)

        binding.vhAssettypeAdd.setOnClickListener {
            listener.add()
        }
    }
}