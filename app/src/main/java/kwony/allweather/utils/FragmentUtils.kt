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
}