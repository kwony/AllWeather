package kwony.allweather.maintab.drawer

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.R
import kwony.allweather.databinding.ViewholderAccountBinding

class AccountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ViewholderAccountBinding

    companion object {
        @JvmStatic
        fun newInstance(parent: ViewGroup): AccountViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_account, parent, false)
            return AccountViewHolder(view)
        }
    }

    fun bind(itemType: AccountTypeAdapterItem, listener: MainAccountAdapterListener) {
        binding = ViewholderAccountBinding.bind(itemView)

        itemView.setOnClickListener {
            listener.clickAccount(itemType.item)
        }

        if (itemType.item.selected) {
            binding.accountName.setTextColor(0xff141414.toInt())
            binding.accountName.text = SpannableString(itemType.item.accountMeta.accountName).apply {
                setSpan(StyleSpan(Typeface.BOLD), 0, this.length, 0)
            }
        } else {
            binding.accountName.setTextColor(0xff989898.toInt())
            binding.accountName.text = itemType.item.accountMeta.accountName
        }
    }
}