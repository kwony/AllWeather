package kwony.allweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kwony.allweather.maintab.MainFragment
import kwony.allweather.utils.FragmentUtils

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentUtils.replaceFragment(supportFragmentManager, android.R.id.content, "mainFragment",
            MainFragment()
        )
    }
}