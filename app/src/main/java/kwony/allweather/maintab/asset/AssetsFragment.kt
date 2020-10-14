package kwony.allweather.maintab.asset

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kwony.allweather.R
import kwony.allweather.databinding.FragmentAssetsBinding
import kwony.allweather.maintab.MainViewModel

@AndroidEntryPoint
class AssetsFragment: Fragment(R.layout.fragment_assets) {

    private lateinit var assetBinding: FragmentAssetsBinding

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        assetBinding = FragmentAssetsBinding.bind(view)

        init()
    }

    fun init() {
        assetBinding.frAssetRv

        assetBinding.frAssetAdd.setOnClickListener {
            // todo: 추가하는거 만들어야함
        }
    }

    fun observe() {
        mainViewModel.currentAssetList.observe(viewLifecycleOwner, Observer {

        })
    }

}