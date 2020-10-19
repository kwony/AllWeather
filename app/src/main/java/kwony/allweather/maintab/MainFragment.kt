package kwony.allweather.maintab

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kwony.allweather.R
import kwony.allweather.databinding.FragmentMainBinding
import kwony.allweather.utils.FragmentUtils
import kwony.allweather.utils.Logger

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    lateinit var binding: FragmentMainBinding

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        FragmentUtils.replaceFragment(
            childFragmentManager,
            binding.frManageContent.id,
            "manageFragment",
            MainTabFragment()
        )

        observe()
    }

    private fun observe() {
        mainViewModel.currentAccount.observe(viewLifecycleOwner, Observer {

            Logger.d("accountMeta: $it")

            binding.frMainTitleBarTitle.text = it.accountName
        })
    }
}