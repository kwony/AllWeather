package kwony.allweather.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_account_editor.*
import kwony.allweather.R
import kwony.allweather.common.TypeAdapterItem
import kwony.allweather.data.asset.AssetTypeMeta
import kwony.allweather.utils.FragmentUtils

@AndroidEntryPoint
class AccountEditorDialogFragment: DialogFragment() {
    companion object {
        private const val KEY_CREATION_MODE = "key_creation_mode"
        private const val KEY_ACCOUNT_ID = "key_account_id"

        @JvmStatic
        fun newInstance(creationMode: Boolean, accountId: Long): AccountEditorDialogFragment {
            val bundle = Bundle().apply {
                putBoolean(KEY_CREATION_MODE, creationMode)
                putLong(KEY_ACCOUNT_ID, accountId)
            }

            return AccountEditorDialogFragment().apply {
                arguments = bundle
            }
        }
    }

    private val accountEditorViewModel: AccountEditorViewModel by viewModels()

    private lateinit var adapter: AssetEditorTypeAdapter

    private val listener = object: AccountAssetTypeAdapterListener {
        override fun add() {
            AssetTypeEditorDialogFragment.newInstance(true).apply {
                doneCallback = { assetTypeMeta ->
                    assetTypeMeta?.run {
                        accountEditorViewModel.addAssetType(this)
                    }
                }
            }.run {
                FragmentUtils.addFragmentIfNotExists(
                    childFragmentManager,
                    "assetTypeEditorFragment",
                    this,
                    true
                )
            }
        }

        override fun edit(assetTypeMeta: AssetTypeMeta) {
            AssetTypeEditorDialogFragment.newInstance(false, assetTypeMeta).apply {
                doneCallback = { assetTypeMeta ->
                    assetTypeMeta?.run {
                        accountEditorViewModel.editAssetType(this)
                    }
                }

            }.run {
                FragmentUtils.addFragmentIfNotExists(
                    childFragmentManager,
                    "assetTypeEditorFragment",
                    this,
                    true
                )
            }
        }

        override fun delete(assetTypeMeta: AssetTypeMeta) {

        }
    }

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

        accountEditorViewModel.init(creationMode, accountId)

        adapter = AssetEditorTypeAdapter(listener)

        fr_account_editor_titlebar.leftIvClick.setOnClickListener {
            dismissAllowingStateLoss()
        }

        fr_account_editor_titlebar.rightText.text = if (creationMode) {
            requireContext().getString(R.string.common_add)
        } else {
            requireContext().getString(R.string.common_edit)
        }

        fr_account_editor_titlebar.rightText.setOnClickListener {
            accountEditorViewModel.done(name = fr_account_editor_name_edit.text.toString())
        }
    }

    private fun observe() {
        accountEditorViewModel.assetTypes.observe(viewLifecycleOwner, Observer { assetTypes ->
            val items = if (assetTypes.size < 10) {
                ArrayList<TypeAdapterItem<AssetTypeViewType>>().apply {
                    addAll(assetTypes.map { AssetTypeAdapterItem(it) })
                    add(AssetTypeAdapterEmptyItem())
                }.toList()
            } else {
                assetTypes.map { AssetTypeAdapterItem(it) }
            }

            adapter.submitItems(items)
        })

        accountEditorViewModel.editingAccountMeta.observe(viewLifecycleOwner, Observer {
            fr_account_editor_name_edit.setText(it.accountName)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account_editor, container, false)
    }
}