package kwony.allweather.maintab

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kwony.allweather.arch.toObservable
import kwony.allweather.data.account.AccountMeta
import kwony.allweather.data.account.AccountRepository
import kwony.allweather.data.asset.AssetMeta
import kwony.allweather.data.asset.AssetRepository
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application
): AndroidViewModel(application) {

    @Inject lateinit var accountRepository: AccountRepository
    @Inject lateinit var assetRepository: AssetRepository

    val currentAccountId: MutableLiveData<Long> = MutableLiveData()
    val currentAccount: MutableLiveData<AccountMeta> = MutableLiveData()
    val currentAssetList: MutableLiveData<List<AssetMeta>> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.addAll(
            currentAccountId.toObservable().distinctUntilChanged()
                .flatMap { accountId ->
                    assetRepository.getAssetMetaList(accountId).toObservable()
                        .doOnNext { currentAssetList.value = it }
                }
                .subscribeOn(Schedulers.io())
                .subscribe(),

            currentAccountId.toObservable().distinctUntilChanged()
                .flatMap { accountId ->
                    accountRepository.getAccountMeta(accountId).toObservable()
                        .doOnNext { currentAccount.value = it }
                }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

}