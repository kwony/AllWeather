package kwony.allweather.maintab.drawer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.common.TypeAdapter
import kwony.allweather.common.TypeAdapterItem
import kwony.allweather.data.account.AccountMeta
import kwony.allweather.maintab.AccountListItem
import java.lang.IllegalArgumentException

class MainAccountAdapter(private val listener: MainAccountAdapterListener): TypeAdapter<MainAccountAdapterViewType, RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MainAccountAdapterViewType.ACCOUNT.ordinal -> AccountViewHolder.newInstance(parent)
            MainAccountAdapterViewType.ADD.ordinal -> AccountAddViewHolder.newInstance(parent)
            else -> throw IllegalArgumentException("Unsupported view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AccountViewHolder -> holder.bind(items[position] as AccountTypeAdapterItem, listener)
            is AccountAddViewHolder -> holder.bind(listener)
        }
    }
}

interface MainAccountAdapterListener {
    fun clickAccountAdd() {}
    fun clickAccount(item: AccountListItem) {}
}

enum class MainAccountAdapterViewType {
    ACCOUNT, ADD
}

data class AccountTypeAdapterItem(val item: AccountListItem): TypeAdapterItem<MainAccountAdapterViewType> {
    override val itemId: Long get() = this.hashCode().toLong()
    override val viewType: MainAccountAdapterViewType get() = MainAccountAdapterViewType.ACCOUNT
}

data class AccountTypeAdapterAddItem(val nothing: Any?): TypeAdapterItem<MainAccountAdapterViewType> {
    override val itemId: Long get() = this.hashCode().toLong()
    override val viewType: MainAccountAdapterViewType get() = MainAccountAdapterViewType.ADD
}
