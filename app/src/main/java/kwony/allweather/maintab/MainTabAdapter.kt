package kwony.allweather.maintab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kwony.allweather.maintab.asset.AssetsFragment
import kwony.allweather.maintab.detail.DetailFragment
import kwony.allweather.maintab.graph.GraphFragment

class MainTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val items: ArrayList<MainTabType> = ArrayList()

    override fun createFragment(position: Int): Fragment {
        return when (items[position]) {
            MainTabType.ASSET -> AssetsFragment()
            MainTabType.CHART -> DetailFragment()
            MainTabType.GRAPH -> GraphFragment()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitItems(items: List<MainTabType>) {
        val oldCount = items.size
        this.items.addAll(items)

        notifyItemRangeChanged(oldCount, items.size)
    }
}

enum class MainTabType {
    ASSET, GRAPH, CHART
}