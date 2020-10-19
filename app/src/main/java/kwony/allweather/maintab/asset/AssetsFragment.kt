package kwony.allweather.maintab.asset

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kwony.allweather.R
import kwony.allweather.data.asset.AssetMeta
import kwony.allweather.databinding.FragmentAssetsBinding
import kwony.allweather.editor.AssetEditorDialogFragment
import kwony.allweather.maintab.MainViewModel
import kwony.allweather.utils.FragmentUtils

@AndroidEntryPoint
class AssetsFragment: Fragment(R.layout.fragment_assets) {

    private lateinit var assetBinding: FragmentAssetsBinding
    private lateinit var adapter : AssetAdapter

    private val mainViewModel: MainViewModel by activityViewModels()

    private val adapterClickListener: AssetAdapterClickListener = object : AssetAdapterClickListener {
        override fun editClick(assetMeta: AssetMeta) {
            FragmentUtils.addFragmentIfNotExists(
                childFragmentManager,
                "assetEditorFragment",
                AssetEditorDialogFragment.newInstance(true, mainViewModel.currentAccount.value!!.accountId, assetId = assetMeta.assetId), // todo: accountId 셋팅해주
                true
            )
        }

        override fun deleteClick(assetMeta: AssetMeta) {
            // todo: delete 하기
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        observe()
    }

    private fun init(view: View) {
        adapter = AssetAdapter(adapterClickListener)
        assetBinding = FragmentAssetsBinding.bind(view)

        assetBinding.frAssetRv.adapter = adapter

        assetBinding.frAssetAdd.setOnClickListener {
            FragmentUtils.addFragmentIfNotExists(childFragmentManager,
                "assetEditorFragment",
                AssetEditorDialogFragment.newInstance(true, mainViewModel.currentAccount.value!!.accountId),
                true
            )
        }
    }

    private fun observe() {
        mainViewModel.currentAssetList.observe(viewLifecycleOwner, Observer {
            adapter.submitItems(it)
        })
    }
}