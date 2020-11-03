package kwony.allweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kwony.allweather.common.BaseActivity
import kwony.allweather.maintab.MainFragment
import kwony.allweather.maintab.MainViewModel
import kwony.allweather.utils.FragmentUtils

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.isReady.observe(this, Observer {
            if (it) {
                FragmentUtils.replaceFragment(supportFragmentManager, android.R.id.content, "mainFragment",
                    MainFragment()
                )
            }
        })

        mainViewModel.ready()
    }
}