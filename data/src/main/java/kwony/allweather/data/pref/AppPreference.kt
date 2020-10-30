package kwony.allweather.data.pref

import android.content.Context
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences

class AppPreference(context: Context) {
    private val rxSharedPreference: RxSharedPreferences = RxSharedPreferences.create(context.getSharedPreferences("AppSharedPref", Context.MODE_PRIVATE))

    private val selectedAccountId: Preference<Long> = rxSharedPreference.getLong("selectedAccountId", 0L)

    fun updateSelectedAccountId(accountId: Long) {
        selectedAccountId.set(accountId)
    }

    fun selectedAccountIdObservable() = selectedAccountId.asObservable()

    fun selectedAccountIdAsValue() = selectedAccountId.get()
}