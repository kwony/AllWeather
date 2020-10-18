package kwony.allweather.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_asset_editor.*
import kwony.allweather.R

@AndroidEntryPoint
class AssetEditorDialogFragment: DialogFragment() {

    companion object {
        private const val KEY_CREATION_MODE = "key_creation_mode"
        private const val KEY_ACCOUNT_ID = "key_account_id"
        private const val KEY_ASSET_ID = "key_asset_id"

        @JvmStatic
        fun newInstance(creationMode: Boolean, accountId: Long, assetId: Long = 0L): AssetEditorDialogFragment {
            val bundle = Bundle().apply {
                putBoolean(KEY_CREATION_MODE, creationMode)
                putLong(KEY_ACCOUNT_ID, accountId)
                putLong(KEY_ASSET_ID, assetId)
            }

            return AssetEditorDialogFragment().apply {
                arguments = bundle
            }
        }
    }

    private val assetEditorViewModel: AssetEditorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_Light_BottomUp)



        observe()
    }

    private fun observe() {

        assetEditorViewModel.assetTypes.observe(viewLifecycleOwner, Observer {
            it.forEach {
                val radioButton = RadioButton(requireContext())
                radioButton.text = it.assetTypeName
                fr_asset_editor_type_group.addView(radioButton)
            }
        })

        assetEditorViewModel.editingAssetMeta.observe(viewLifecycleOwner, Observer {
            fr_asset_editor_name_edit.setText(it.assetName)
            fr_asset_editor_amount_edit.setText(it.assetAmount)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_asset_editor, container, false)
    }
}