package kwony.allweather.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_asset_type_editor.*
import kwony.allweather.R
import kwony.allweather.data.asset.AssetTypeMeta

class AssetTypeEditorDialogFragment: DialogFragment() {
    companion object {
        private const val KEY_CREATION_MODE = "key_creation_mode"
        private const val KEY_ASSET_TYPE_META = "key_asset_type_meta"

        @JvmStatic
        fun newInstance(creationMode: Boolean, assetTypeMeta: AssetTypeMeta? = null): AssetTypeEditorDialogFragment {
            val bundle = Bundle().apply {
                putBoolean(KEY_CREATION_MODE, creationMode)
                putParcelable(KEY_ASSET_TYPE_META, assetTypeMeta)
            }

            return AssetTypeEditorDialogFragment().apply { arguments = bundle }
        }
    }

    var doneCallback: ((AssetTypeMeta?) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_Light_Enter)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
    }

    private fun init() {
        val creationMode = arguments?.getBoolean(KEY_CREATION_MODE)?: true
        val assetTypeMeta = arguments?.getParcelable<AssetTypeMeta>(KEY_ASSET_TYPE_META)?: null

        fr_asset_type_editor_done.text = if (creationMode) {
            context?.getString(R.string.common_add)
        } else {
            context?.getString(R.string.common_edit)
        }

        if (!creationMode && assetTypeMeta != null) {
            fr_asset_type_editor_name_edit.setText(assetTypeMeta.assetTypeName)
            fr_asset_type_editor_weight_edit.setText(assetTypeMeta.targetWeight.toString())
        }

        fr_asset_type_editor_titlebar.rightIvClick.setOnClickListener {
            dismissAllowingStateLoss()
        }

        fr_asset_type_editor_done.setOnClickListener {
            if (creationMode) {
                val createdAssetType = AssetTypeMeta(
                    assetTypeId = 0L,
                    accountId = 0L,
                    assetTypeName = fr_asset_type_editor_name_edit.text.toString(),
                    targetWeight = fr_asset_type_editor_weight_edit.text.toString().toInt()
                )

                doneCallback?.invoke(createdAssetType)
                dismissAllowingStateLoss()
            } else {

                assetTypeMeta!!.apply {
                    assetTypeName = fr_asset_type_editor_name_edit.text.toString()
                    targetWeight = fr_asset_type_editor_weight_edit.text.toString().toInt()
                }

                doneCallback?.invoke(assetTypeMeta)
                dismissAllowingStateLoss()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_asset_type_editor, container, false)
    }
}

