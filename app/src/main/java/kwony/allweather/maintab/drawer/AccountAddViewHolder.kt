package kwony.allweather.maintab.drawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.R
import kwony.allweather.databinding.ViewholderAccountAddBinding

class AccountAddViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ViewholderAccountAddBinding

    companion object {
        @JvmStatic
        fun newInstance(parent: ViewGroup): AccountAddViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_account_add, parent, false)
            return AccountAddViewHolder(view)
        }
    }

    fun bind(listener: MainAccountAdapterListener) {
        binding = ViewholderAccountAddBinding.bind(itemView)

        itemView.setOnClickListener { listener.clickAccountAdd() }
    }
}