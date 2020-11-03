package kwony.allweather.maintab.drawer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_main_drawer_view.view.*
import kwony.allweather.R
import kwony.allweather.maintab.AccountDetailItem
import kwony.allweather.utils.WonUtils

class MainDrawerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_main_drawer_view, this)
    }

    val accountRv: RecyclerView get() = layout_drawer_account_list

    fun setSelectedAccount(accountDetailItem: AccountDetailItem) {
        layout_drawer_selected_account_name.text = accountDetailItem.accountMeta.accountName
        layout_drawer_selected_account_amount.text = WonUtils.wonString(context, accountDetailItem.accountSum)

        layout_drawer_selected_account_score_desc.text = when (accountDetailItem.score) {
            in 80..100 -> {
                setStarScore(5)
                context.getString(R.string.account_score_80_100)
            }
            in 60..80 -> {
                setStarScore(4)
                context.getString(R.string.account_score_60_80)
            }
            in 40..60 -> {
                setStarScore(3)
                context.getString(R.string.account_score_40_60)
            }
            in 20..40 -> {
                setStarScore(2)
                context.getString(R.string.account_score_20_40)
            }
            else -> {
                setStarScore(1)
                context.getString(R.string.account_score_0_20)
            }
        }
    }

    private fun setStarScore(filledCount: Int) {
        var count = filledCount

        layout_drawer_selected_account_score.children.forEach {
            if (it is ImageView) {
                if (count > 0) {
                    it.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_filled))
                } else {
                    it.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_unfilled))
                }
                count--
            }
        }
    }
}