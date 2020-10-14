package kwony.allweather.maintab

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kwony.allweather.R
import kwony.allweather.databinding.FragmentMainTabBinding

@AndroidEntryPoint
class MainTabFragment: Fragment(R.layout.fragment_main_tab) {

    private val mainViewModel: MainViewModel by activityViewModels()

    lateinit var binding: FragmentMainTabBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
    }

    fun init() {

    }

    private fun observe() {
    }
}