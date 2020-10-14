package kwony.allweather.maintab

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
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

        binding.frManageViewpager.adapter = adapter
        binding.frManageViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.frManageViewpager.isUserInputEnabled = false

        binding.frManageAssets.setOnClickListener {
            binding.frManageViewpager.setCurrentItem(assetPosition, false)
        }
        binding.frManageGraph.setOnClickListener {
            binding.frManageViewpager.setCurrentItem(graphPosition, false)
        }
        binding.frManageChart.setOnClickListener {
            binding.frManageViewpager.setCurrentItem(chartPosition, false)
        }

    }
}