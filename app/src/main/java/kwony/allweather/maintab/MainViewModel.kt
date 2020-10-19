package kwony.allweather.maintab

import android.app.Application
import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kwony.allweather.AllWeatherApp
import kwony.allweather.arch.setValueSafely
import kwony.allweather.arch.toObservable
import kwony.allweather.data.account.AccountMeta
import kwony.allweather.data.account.AccountRepository
import kwony.allweather.data.asset.AssetMeta
import kwony.allweather.data.asset.AssetRepository
import kwony.allweather.data.asset.AssetTypeRepository
import kwony.allweather.model.AssetTypeDefaults
import kwony.allweather.utils.Logger
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application,
    private val accountRepository: AccountRepository,
    private val assetRepository: AssetRepository,
    private val assetTypeRepository: AssetTypeRepository
): AndroidViewModel(application) {

    private val currentAccountId: BehaviorSubject<Long> = BehaviorSubject.create()
    val currentAccount: MutableLiveData<AccountMeta> = MutableLiveData()
    val currentAssetList: MutableLiveData<List<AssetMeta>> = MutableLiveData()

    val isReady: MutableLiveData<Boolean> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.addAll(
            currentAccountId
                .flatMap { accountId ->
                    Logger.d("accountId: $accountId")
                    assetRepository.getAssetMetaList(accountId).toObservable()
                        .doOnNext {
                            Logger.d("list: $it")
                            currentAssetList.setValueSafely(it)
                        }
                }
                .subscribeOn(Schedulers.io())
                .subscribe(),

            currentAccountId
                .flatMap { accountId ->
                    Logger.d("accountId: $accountId")
                    accountRepository.getAccountMeta(accountId).toObservable()
                        .doOnNext {
                            Logger.d("accountMeta: $it")
                            currentAccount.setValueSafely(it)
                        }
                }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun ready() {
        compositeDisposable.add(
            accountRepository.getAccountMetaList()
                .flatMap {
                    if (it.isEmpty()) {
                        createDefaultAccount()
                    } else {
                        Flowable.fromCallable {
                            it.first().accountId
                        }
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { accountId ->
                    Logger.d("accountId: $accountId")

                    currentAccountId.onNext(accountId)
                    isReady.setValueSafely(true)
                }
                .subscribe()
        )
    }

    private fun createDefaultAccount() = Flowable.fromCallable {
        val accountMeta = AccountMeta(
            accountId = 0L,
            accountName = "All Weather Account",
            isDefault = true
        )
        accountRepository.upsert(
            accountMeta = accountMeta
        )
    }
        .doOnNext { accountId ->
            val list =
                AssetTypeDefaults.createDefaultAssetTypes(getApplication<AllWeatherApp>().applicationContext)
                    .apply {
                        this.forEach { it.accountId = accountId }
                    }
            assetTypeRepository.upsert(list)
        }
        .subscribeOn(Schedulers.io())

}