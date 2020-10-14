package kwony.allweather.common

import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

abstract class SimpleAdapter<T, VH: RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    var items: List<T> = ArrayList()

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int): T = items.elementAt(position)

    fun submitItems(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }
}