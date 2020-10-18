package kwony.allweather.common

import androidx.recyclerview.widget.RecyclerView

abstract class TypeAdapter<VT: Enum<VT>, VH: RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    var items: List<TypeAdapterItem<VT>?> = emptyList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return items.elementAt(position)!!.itemId
    }

    override fun getItemViewType(position: Int): Int {
        return items.elementAt(position)!!.viewType.ordinal
    }

    fun <T : TypeAdapterItem<VT>?> getItem(position: Int): T? {
        return items.elementAtOrNull(position) as T
    }

    fun submitItems(items: List<TypeAdapterItem<VT>>) {
        this.items = items
        notifyDataSetChanged()
    }
}

interface TypeAdapterItem<T: Enum<T>> {
    val itemId: Long

    val viewType: T
}