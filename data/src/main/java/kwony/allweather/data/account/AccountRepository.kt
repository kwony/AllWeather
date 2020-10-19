package kwony.allweather.data.account

import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor() {
    @Inject lateinit var accountDao: AccountDao

    fun getAccountMeta(accountId: Long): Flowable<AccountMeta> {
        return accountDao.getEntity(accountId)
    }

    fun getAccountMetaList(): Flowable<List<AccountMeta>> {
        return accountDao.getEntities()
    }

    fun upsert(accountMeta: AccountMeta): Long {
        return accountDao.upsert(accountMeta)
    }
}