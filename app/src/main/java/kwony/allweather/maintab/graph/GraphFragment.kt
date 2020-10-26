package kwony.allweather.maintab.graph

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import kwony.allweather.R
import kwony.allweather.databinding.FragmentGraphBinding
import kwony.allweather.maintab.MainViewModel
import kwony.allweather.utils.Logger
import java.util.*

@AndroidEntryPoint
class GraphFragment: Fragment(R.layout.fragment_graph) {

    private lateinit var graphBinding: FragmentGraphBinding

    private val mainViewModel: MainViewModel by activityViewModels()

    companion object {
        val CHART_COLORS = intArrayOf(
            Color.rgb(128, 0, 0),
            Color.rgb(0, 128, 4),
            Color.rgb(0, 128, 89),
            Color.rgb(0, 72, 128),
            Color.rgb(38, 0, 128),
            Color.rgb(123, 0, 128)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        observe()
    }

    private fun init(view: View) {
        graphBinding = FragmentGraphBinding.bind(view)

    }

    private fun observe() {
        mainViewModel.currentAssetTypeItems.observe(viewLifecycleOwner, Observer { list ->
            val entries = list.filter { it.assetTypeSum > 0 }.map {
                PieEntry(it.assetTypeSum.toFloat(), it.assetTypeMeta.assetTypeName)
            }

            val dataSet = PieDataSet(entries, "Asset Type Percentage")

            // add a lot of colors
            val colors = ArrayList<Int>()

            for (c in CHART_COLORS) colors.add(c)

            dataSet.colors = colors

            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextSize(11f)
            data.setValueTextColor(Color.WHITE)

            graphBinding.frGraphChart.setEntryLabelColor(Color.WHITE)
            graphBinding.frGraphChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD)
            graphBinding.frGraphChart.setUsePercentValues(true)

            graphBinding.frGraphChart.contentDescription = ""
            graphBinding.frGraphChart.description.text = ""

            graphBinding.frGraphChart.data = data
            graphBinding.frGraphChart.invalidate()
        })
    }
}