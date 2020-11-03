package kwony.allweather.common

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class BaseFragment(@LayoutRes contentLayoutId: Int): Fragment(contentLayoutId) {
    fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.primaryNavigationFragment

        return fragment is BaseFragment && fragment.onBackPressed() || handleBackPressed()
    }

    open fun handleBackPressed(): Boolean {
        return false
    }
}