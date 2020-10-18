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

@AndroidEntryPoint
class AccountEditorDialogFragment: DialogFragment() {

    private val accountEditorViewModel: AccountEditorViewModel by viewModels()

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_Light_BottomUp)

        init()
        observe()
    }


    private fun init() {
        val creationMode = arguments?.getBoolean(KEY_CREATION_MODE)?: true
        val accountId = arguments?.getLong(KEY_ACCOUNT_ID)?: 0L

        accountEditorViewModel.init(creationMode, accountId)

        fr_account_editor_titlebar.leftClick.setOnClickListener {
            dismissAllowingStateLoss()
        }

        fr_account_editor_titlebar.rightClick.setOnClickListener {
            accountEditorViewModel.done(name = fr_account_editor_name_edit.text.toString())
        }
    }

    private fun observe() {
        accountEditorViewModel.assetTypes.observe(viewLifecycleOwner, Observer {

        })

        accountEditorViewModel.editingAccountMeta.observe(viewLifecycleOwner, Observer {
            fr_account_editor_name_edit.setText(it.accountName)
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_asset_editor, container, false)
    }


}