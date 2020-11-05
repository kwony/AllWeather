package kwony.allweather.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_confirm_dialog.*
import kwony.allweather.R

class ConfirmDialogFragment: DialogFragment() {
    companion object {
        private const val KEY_TITLE = "key_title"
        private const val KEY_SUB_TITLE = "key_sub_title"

        @JvmStatic
        fun newInstance(title: String, subTitle: String): ConfirmDialogFragment {
            val bundle = Bundle().apply {
                putString(KEY_TITLE, title)
                putString(KEY_SUB_TITLE, subTitle)
            }

            return ConfirmDialogFragment().apply { arguments = bundle }
        }
    }

    var okayCallback: (() -> Unit)? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_Light_Enter)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.getString(KEY_TITLE)?.let {
            fr_confirm_dialog_title.text = it
        }

        arguments?.getString(KEY_SUB_TITLE)?.let {
            fr_confirm_dialog_subtitle.text = it
        }


        fr_confirm_dialog_cancel.setOnClickListener {
            dismissAllowingStateLoss()
        }

        fr_confirm_dialog_done.setOnClickListener {
            okayCallback?.invoke()
            dismissAllowingStateLoss()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_confirm_dialog, container, false)
    }
}