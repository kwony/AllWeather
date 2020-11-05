package kwony.allweather.maintab.asset

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kwony.allweather.R
import kwony.allweather.common.ConfirmDialogFragment
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
                AssetEditorDialogFragment.newInstance(false, mainViewModel.currentAccountLiveData.value!!.accountId, assetId = assetMeta.assetId), // todo: accountId 셋팅해주
                true
            )
        }

        override fun deleteClick(assetMeta: AssetMeta) {
            val dialog = ConfirmDialogFragment.newInstance(requireContext().getString(R.string.asset_delete), requireContext().getString(R.string.common_delete_subtitle)).apply {
                this.okayCallback = {
                    mainViewModel.deleteAsset(assetMeta.assetId)
                }
            }

            dialog.show(childFragmentManager, null)
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
        assetBinding.frAssetRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        assetBinding.frAssetAdd.setOnClickListener {
            FragmentUtils.addFragmentIfNotExists(childFragmentManager,
                "assetEditorFragment",
                AssetEditorDialogFragment.newInstance(true, mainViewModel.currentAccountLiveData.value!!.accountId),
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