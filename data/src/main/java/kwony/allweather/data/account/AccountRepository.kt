package kwony.allweather.data.account

import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor() {
    @Inject lateinit var accountDao: AccountDao

    fun getAccountMetaList(): Flowable<List<AccountMeta>> {
        return accountDao.getEntities()
    }
}