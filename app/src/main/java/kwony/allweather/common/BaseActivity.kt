package kwony.allweather.common

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    override fun onBackPressed() {
        val fragment = supportFragmentManager.primaryNavigationFragment

        if (fragment != null && fragment is BaseFragment && fragment.onBackPressed()) {

        } else {
            handleBackPressed()
        }
    }

    protected fun handleBackPressed() {
        super.onBackPressed()
    }
}