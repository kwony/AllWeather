package kwony.allweather.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object FragmentUtils {
    fun replaceFragment(
        fragmentManager: FragmentManager, @IdRes contentId: Int,
        tag: String?,
        fragment: Fragment
    ) {
        fragmentManager
            .beginTransaction()
            .replace(contentId, fragment, tag)
            .setPrimaryNavigationFragment(fragment)
            .commitNowAllowingStateLoss()
    }

    fun addFragmentIfNotExists(
        fragmentManager: FragmentManager,
        tag: String?,
        fragment: Fragment,
        now: Boolean
    ): Boolean {
        if (hasFragment(fragmentManager, tag)) {
            return false
        }
        val fragmentTransaction = fragmentManager
            .beginTransaction()
            .add(fragment, tag)
        if (now) {
            fragmentTransaction.commitNowAllowingStateLoss()
        } else {
            fragmentTransaction.commitAllowingStateLoss()
        }
        return true
    }

    private fun hasFragment(fragmentManager: FragmentManager, tag: String?): Boolean {
        return fragmentManager.findFragmentByTag(tag) != null
    }
}