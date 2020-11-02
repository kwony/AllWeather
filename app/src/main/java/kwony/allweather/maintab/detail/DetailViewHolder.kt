package kwony.allweather.maintab.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.R
import kwony.allweather.databinding.ViewholderDetailBinding
import kwony.allweather.maintab.AssetTypeItem
import kwony.allweather.utils.NumberUtils
import java.text.NumberFormat
import java.util.*

class DetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ViewholderDetailBinding

    companion object {
        @JvmStatic
        fun newInstance(parent: ViewGroup): DetailViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_detail, parent, false)
            return DetailViewHolder(view)
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: AssetTypeItem, listener: DetailAdapterListener) {
        binding = ViewholderDetailBinding.bind(itemView)

        binding.vhDetailAssetTypeName.text = item.assetTypeMeta.assetTypeName
        binding.vhDetailAssetTypeAmount.text = NumberUtils.wonString(itemView.context, item.assetTypeSum)
        binding.vhDetailAssetTypeTargetAmount.text = NumberUtils.wonString(itemView.context, item.targetSum)
        binding.vhDetailAssetTypeWeight.text = String.format("%s", (item.assetTypePercentage * 100).toInt().toString()) + "%"
        binding.vhDetailAssetTypeTargetWeight.text = String.format("%s", (item.assetTypeMeta.targetWeight).toString()) + "%"

        val diffSum = item.targetSum - item.assetTypeSum

        if (diffSum >= 0) {
            binding.vhDetailAssetTypeChangeAmount.text = String.format(
                itemView.context.getString(R.string.detail_need_to_raise), NumberFormat.getInstance(
                    Locale.getDefault()
                ).format(diffSum)
            )
            binding.vhDetailAssetTypeChangeAmount.setTextColor(itemView.context.getColor(android.R.color.holo_red_dark))
        } else {
            binding.vhDetailAssetTypeChangeAmount.text = String.format(
                itemView.context.getString(R.string.detail_need_to_down), NumberFormat.getInstance(
                    Locale.getDefault()
                ).format(-diffSum)
            )
            binding.vhDetailAssetTypeChangeAmount.setTextColor(itemView.context.getColor(android.R.color.holo_blue_dark))
        }

        binding.vhDetailEdit.setOnClickListener {
            listener.clickEdit(item)
        }
        binding.vhDetailDelete.setOnClickListener {
            listener.clickDelete(item)
        }
    }
}