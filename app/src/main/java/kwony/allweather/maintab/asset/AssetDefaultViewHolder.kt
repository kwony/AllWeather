package kwony.allweather.maintab.asset

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.viewholder_asset_default.view.*
import kwony.allweather.R
import kwony.allweather.data.asset.AssetMeta
import kwony.allweather.databinding.ViewholderAssetDefaultBinding
import java.text.NumberFormat
import java.util.*

class AssetDefaultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: ViewholderAssetDefaultBinding

    companion object {
        @JvmStatic
        fun newInstance(parent: ViewGroup): AssetDefaultViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_asset_default, parent, false)
            return AssetDefaultViewHolder(view)
        }
    }

    fun bind(assetMeta: AssetMeta, listener: AssetAdapterClickListener?) {
        binding = ViewholderAssetDefaultBinding.bind(itemView)
        binding.vhAssetName.text = assetMeta.assetName
        binding.vhAssetAmount.text = NumberFormat.getInstance(Locale.getDefault()).format(assetMeta.assetAmount)

        RxView.clicks(binding.vhAssetEdit)
            .doOnNext { listener?.editClick(assetMeta) }
            .subscribe()

        RxView.clicks(binding.vhAssetDelete)
            .doOnNext { listener?.deleteClick(assetMeta) }
            .subscribe()
    }
}