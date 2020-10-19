package kwony.allweather.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        observe()
    }

    private fun init() {
        val creationMode = arguments?.getBoolean(KEY_CREATION_MODE)?: true
        val accountId = arguments?.getLong(KEY_ACCOUNT_ID)?: 0L
        val assetId = arguments?.getLong(KEY_ASSET_ID)?: 0L

        assetEditorViewModel.init(creationMode = creationMode, accountId = accountId, assetId = assetId)

        fr_asset_editor_titlebar.leftIvClick.setOnClickListener {
            dismissAllowingStateLoss()
        }

        fr_asset_editor_titlebar.rightText.setOnClickListener {
            if (fr_asset_editor_amount_desc.text.isBlank() || fr_asset_editor_name_desc.text.isBlank()
                || assetEditorViewModel.assetTypes.value!!.getOrNull(getCheckedButtonIndex()) == null) {
                // error toast
                return@setOnClickListener;
            }

            assetEditorViewModel.done(
                name = fr_asset_editor_name_edit.text.toString(),
                amount = fr_asset_editor_amount_edit.text.toString().toInt(),
                typeId = assetEditorViewModel.assetTypes.value!![getCheckedButtonIndex()].assetTypeId
            )
        }
    }

    private fun observe() {
        assetEditorViewModel.assetTypes.observe(viewLifecycleOwner, Observer { list ->
            list.forEach {
                val radioButton = RadioButton(requireContext())
                radioButton.text = it.assetTypeName
                fr_asset_editor_type_group.addView(radioButton)
            }

            (fr_asset_editor_type_group.children.filter { it is RadioButton }.first() as RadioButton).isChecked = true
        })

        assetEditorViewModel.editingAssetMeta.observe(viewLifecycleOwner, Observer {
            fr_asset_editor_name_edit.setText(it.assetName)
            fr_asset_editor_amount_edit.setText(it.assetAmount.toString())
        })

        assetEditorViewModel.done.observe(viewLifecycleOwner, Observer {
            dismissAllowingStateLoss()
        })
    }

    private fun getCheckedButtonIndex(): Int {
        return fr_asset_editor_type_group.children.filter { it is RadioButton }
            .indexOfFirst { it.id == fr_asset_editor_type_group.checkedRadioButtonId }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_asset_editor, container, false)
    }
}