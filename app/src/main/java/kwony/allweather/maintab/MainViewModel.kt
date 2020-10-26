package kwony.allweather.maintab

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kwony.allweather.AllWeatherApp
import kwony.allweather.arch.setValueSafely
import kwony.allweather.data.account.AccountMeta
import kwony.allweather.data.account.AccountRepository
import kwony.allweather.data.asset.AssetMeta
import kwony.allweather.data.asset.AssetRepository
import kwony.allweather.data.asset.AssetTypeMeta
import kwony.allweather.data.asset.AssetTypeRepository
import kwony.allweather.maintab.asset.AssetAdapterItem
import kwony.allweather.model.AssetTypeDefaults
import kwony.allweather.utils.Logger

class MainViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application,
    private val accountRepository: AccountRepository,
    private val assetRepository: AssetRepository,
    private val assetTypeRepository: AssetTypeRepository
): AndroidViewModel(application) {

    private val currentAccountId: BehaviorSubject<Long> = BehaviorSubject.create()
    val currentAccount: MutableLiveData<AccountMeta> = MutableLiveData()
    val currentAssetList: MutableLiveData<List<AssetAdapterItem>> = MutableLiveData()
    val currentAssetTypeItems : MutableLiveData<List<AssetTypeItem>> = MutableLiveData()

    val isReady: MutableLiveData<Boolean> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.addAll(
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
                .subscribe(),

            currentAccountId
                .flatMap {  accountId ->
                    Observable.combineLatest(
                        assetRepository.getAssetMetaList(accountId = accountId).toObservable(),
                        assetTypeRepository.getAssetTypeMetaList(accountId = accountId).toObservable(),
                        BiFunction<List<AssetMeta>, List<AssetTypeMeta>, Pair<List<AssetMeta>, List<AssetTypeMeta>>> {a, b -> Pair(a, b)}
                    )
                        .doOnNext { pair ->
                            val items = pair.first.map {  assetMeta ->
                                val assetTypeMeta = pair.second.find { assetMeta.assetTypeId == it.assetTypeId }
                                AssetAdapterItem(assetMeta, assetTypeMeta)
                            }
                            currentAssetList.setValueSafely(items)
                        }
                        .doOnNext { pair ->
                            val assets = pair.first
                            val assetTypes = pair.second
                            val totalAmount = pair.first.sumBy { it.assetAmount }
                            val assetTypeItems = assetTypes.map {  currentAssetType ->
                                val corresAssets = assets.filter { it.assetTypeId == currentAssetType.assetTypeId }

                                val assetTypeSum = corresAssets.sumBy { it.assetAmount }
                                val assetTypePercentage = assetTypeSum.toFloat() / totalAmount

                                AssetTypeItem(
                                    assetTypeMeta = currentAssetType,
                                    assetTypeSum = assetTypeSum,
                                    assetTypePercentage = assetTypePercentage
                                )
                            }

                            currentAssetTypeItems.setValueSafely(assetTypeItems)
                        }
                }
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

data class AssetTypeItem(
    val assetTypeMeta: AssetTypeMeta,
    val assetTypeSum: Int,
    val assetTypePercentage: Float
)