package kwony.allweather.common

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.layout_titlebar.view.*
import kwony.allweather.R
import kwony.allweather.utils.DimensionUtils

class TitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    val leftIvClick: View get() = layout_title_bar_left_iv_click
    val rightIvClick: View get() = layout_title_bar_right_iv_click

    val rightText: TextView get() = layout_titlebar_right_text

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_titlebar, this)

        val array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0)

        val drawableLeft = array.getDrawable(R.styleable.TitleBar_drawable_left)
        val drawableLeftMargin = array.getDimensionPixelSize(R.styleable.TitleBar_drawable_left_margin, DimensionUtils.dp2px(context, 12f).toInt())

        val drawableRight = array.getDrawable(R.styleable.TitleBar_drawable_right)
        val drawableRightMargin = array.getDimensionPixelSize(R.styleable.TitleBar_drawable_right_margin, DimensionUtils.dp2px(context, 12f).toInt())

        val title = array.getString(R.styleable.TitleBar_title)
        val titleSize = array.getDimensionPixelSize(R.styleable.TitleBar_title_size, DimensionUtils.dp2px(context, 17f).toInt())
        val titleColor = array.getColor(R.styleable.TitleBar_title_color, Color.BLACK)

        val textRight = array.getString(R.styleable.TitleBar_text_right)
        val textRightSize = array.getDimensionPixelSize(R.styleable.TitleBar_text_right_size, DimensionUtils.dp2px(context, 14f).toInt())

        array.recycle()

        title?.let { layout_titlebar_title.text = title }
        titleSize.let { layout_titlebar_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.toFloat()) }
        titleColor.let { layout_titlebar_title.setTextColor(it) }

        textRight?.let {
            layout_titlebar_right_text.text = it
            layout_titlebar_right_text.visibility = View.VISIBLE
        }
        textRightSize.let { layout_titlebar_right_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.toFloat())}

        drawableLeft?.let { layout_titlebar_left_iv.setImageDrawable(drawableLeft) }
        drawableLeftMargin.let { (layout_titlebar_left_iv.layoutParams as MarginLayoutParams).apply { marginStart = it } }

        drawableRight?.let { layout_titlebar_right_iv.setImageDrawable(drawableRight) }
        drawableRightMargin.let { (layout_titlebar_right_iv.layoutParams as MarginLayoutParams).apply { marginEnd = it } }
    }

    fun setTitle(title: String) {
        layout_titlebar_title.text = title
    }
}