package kwony.allweather.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.R
import kwony.allweather.databinding.ViewholderAssetTypeBinding

class AssetTypeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ViewholderAssetTypeBinding

    companion object {
        @JvmStatic
        fun newInstance(parent: ViewGroup): AssetTypeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_asset_type, parent, false)
            return AssetTypeViewHolder(view)
        }
    }

    fun bind(item: AssetTypeAdapterItem, listener: AccountAssetTypeAdapterListener) {
        binding = ViewholderAssetTypeBinding.bind(itemView)

        binding.vhAssettypeDelete.setOnClickListener {
            listener.delete(item.assetTypeMeta)
        }

        binding.vhAssettypeEdit.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_edit))
        binding.vhAssettypeEdit.setOnClickListener { listener.edit(item.assetTypeMeta) }

        binding.vhAssettypeName.text = item.assetTypeMeta.assetTypeName
        binding.vhAssettypeName.visibility = View.VISIBLE

        binding.vhAssettypeWeight.text = String.format("%s", item.assetTypeMeta.targetWeight.toString()) + "%"
        binding.vhAssettypeWeight.visibility = View.VISIBLE
    }
}