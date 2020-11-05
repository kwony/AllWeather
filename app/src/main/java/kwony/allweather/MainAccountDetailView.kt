package kwony.allweather

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import kotlinx.android.synthetic.main.layout_main_account_detail_view.view.*
import kotlinx.android.synthetic.main.layout_main_drawer_view.view.*
import kwony.allweather.maintab.AccountDetailItem
import kwony.allweather.utils.WonUtils

class MainAccountDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        LayoutInflater.from(context).inflate(R.layout.layout_main_account_detail_view, this)
    }

    fun setSelectedAccount(accountDetailItem: AccountDetailItem) {
        layout_main_account_detail_account_sum.text = WonUtils.wonAbsolute(context, accountDetailItem.accountSum)
        layout_main_account_detail_account_sum_readable.text = WonUtils.wonReadable(context, accountDetailItem.accountSum)
        layout_main_account_detail_account_score.text = when (accountDetailItem.score) {
            in 90..100 -> {
                setStarScore(5)
                context.getString(R.string.account_score_80_100)
            }
            in 70..90 -> {
                setStarScore(4)
                context.getString(R.string.account_score_60_80)
            }
            in 50..70 -> {
                setStarScore(3)
                context.getString(R.string.account_score_40_60)
            }
            in 30..50 -> {
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

        layout_main_account_detail_account_score_layout.children.forEach {
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