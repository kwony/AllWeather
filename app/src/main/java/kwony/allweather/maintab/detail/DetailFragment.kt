package kwony.allweather.maintab.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kwony.allweather.R
import kwony.allweather.common.ConfirmDialogFragment
import kwony.allweather.databinding.FragmentDetailBinding
import kwony.allweather.editor.AssetTypeEditorDialogFragment
import kwony.allweather.maintab.AssetTypeItem
import kwony.allweather.maintab.MainViewModel

class DetailFragment: Fragment(R.layout.fragment_detail) {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var detailBinding: FragmentDetailBinding
    private lateinit var adapter: DetailAdapter

    private val adapterClickListener: DetailAdapterListener = object: DetailAdapterListener {
        override fun clickEdit(item: AssetTypeItem) {
            val dialog = AssetTypeEditorDialogFragment.newInstance(false, item.assetTypeMeta).apply {
                doneCallback = { assetTypeMeta ->
                    assetTypeMeta?.run {
                        mainViewModel.upsertAssetType(this)
                    }
                }
            }

            dialog.show(childFragmentManager, null)
        }

        override fun clickDelete(item: AssetTypeItem) {
            val dialog = ConfirmDialogFragment.newInstance(requireContext().getString(R.string.common_delete), requireContext().getString(R.string.common_delete_subtitle)).apply {
                this.okayCallback = {
                    mainViewModel.deleteAssetType(item.assetTypeMeta.assetTypeId)
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
        detailBinding = FragmentDetailBinding.bind(view)

        adapter = DetailAdapter(adapterClickListener)

        detailBinding.rv.adapter = adapter
        detailBinding.rv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun observe() {
        mainViewModel.currentAssetTypeItems.observe(viewLifecycleOwner, Observer {
            adapter.submitItems(it)
        })
    }
}