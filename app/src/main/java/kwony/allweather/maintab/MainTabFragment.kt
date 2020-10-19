package kwony.allweather.maintab

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main_tab.*
import kwony.allweather.R
import kwony.allweather.databinding.FragmentMainTabBinding

@AndroidEntryPoint
class MainTabFragment: Fragment(R.layout.fragment_main_tab) {

    private lateinit var binding: FragmentMainTabBinding

    private lateinit var adapter: MainTabAdapter

    private val adapterItems: List<MainTabType> = listOf(MainTabType.ASSET, MainTabType.GRAPH, MainTabType.CHART)

    private val assetPosition get() = adapterItems.indexOf(MainTabType.ASSET)
    private val graphPosition get() = adapterItems.indexOf(MainTabType.GRAPH)
    private val chartPosition get() = adapterItems.indexOf(MainTabType.CHART)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainTabBinding.bind(view)

        init()
    }

    private fun init() {
        adapter = MainTabAdapter(childFragmentManager, lifecycle).apply {
            this.submitItems(adapterItems)
        }

        binding.frMaintabViewpager.adapter = adapter
        binding.frMaintabViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.frMaintabViewpager.isUserInputEnabled = false

        binding.frMaintabAssets.setOnClickListener {
            binding.frMaintabViewpager.setCurrentItem(assetPosition, false)

            markSelectedText(fr_maintab_assets_name)
            markUnSelectedText(fr_maintab_graph_name)
            markUnSelectedText(fr_maintab_manage_name)
        }
        binding.frMaintabGraph.setOnClickListener {
            binding.frMaintabViewpager.setCurrentItem(graphPosition, false)

            markUnSelectedText(fr_maintab_assets_name)
            markSelectedText(fr_maintab_graph_name)
            markUnSelectedText(fr_maintab_manage_name)
        }
        binding.frMaintabManage.setOnClickListener {
            binding.frMaintabViewpager.setCurrentItem(chartPosition, false)

            markUnSelectedText(fr_maintab_assets_name)
            markUnSelectedText(fr_maintab_graph_name)
            markSelectedText(fr_maintab_manage_name)
        }

    }

    private fun markSelectedText(textView: TextView) {
        textView.setTextColor(0xff141414.toInt())
    }

    private fun markUnSelectedText(textView: TextView) {
        textView.setTextColor(0xff989898.toInt())
    }
}