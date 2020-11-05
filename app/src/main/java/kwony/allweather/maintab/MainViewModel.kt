package kwony.allweather.maintab

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
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
import kwony.allweather.data.pref.AppPreference
import kwony.allweather.maintab.asset.AssetAdapterItem
import kwony.allweather.model.AssetTypeDefaults
import kwony.allweather.utils.Logger
import kotlin.math.pow

class MainViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application,
    private val accountRepository: AccountRepository,
    private val assetRepository: AssetRepository,
    private val assetTypeRepository: AssetTypeRepository,
    private val appPreference: AppPreference
): AndroidViewModel(application) {

    val currentAccountLiveData: MutableLiveData<AccountDetailItem> = MutableLiveData()
    val currentAssetList: MutableLiveData<List<AssetAdapterItem>> = MutableLiveData()
    val currentAssetTypeItems : MutableLiveData<List<AssetTypeItem>> = MutableLiveData()

    val accountListLiveData: MutableLiveData<List<AccountListItem>> = MutableLiveData()

    val isReady: MutableLiveData<Boolean> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.addAll(
            appPreference.selectedAccountIdObservable()
                .filter { it != 0L }
                .flatMap {  selectedAccountId ->
                    Observable.combineLatest(
                        accountRepository.getAccountMetaList().toObservable(),
                        assetRepository.getAssetMetaList(accountId = selectedAccountId).toObservable(),
                        assetTypeRepository.getAssetTypeMetaList(accountId = selectedAccountId).toObservable(),
                        Function3<List<AccountMeta>, List<AssetMeta>, List<AssetTypeMeta>, Triple<List<AccountMeta>, List<AssetMeta>, List<AssetTypeMeta>>> {
                            a, b, c -> Triple(a, b, c)

                        }
                    )
                        .doOnNext { triple ->
                            val items = triple.second.map {  assetMeta ->
                                val assetTypeMeta = triple.third.find { assetMeta.assetTypeId == it.assetTypeId }
                                AssetAdapterItem(assetMeta, assetTypeMeta)
                            }
                            currentAssetList.setValueSafely(items)
                        }
                        .doOnNext { triple ->
                            val accounts = triple.first
                            val assets = triple.second
                            val assetTypes = triple.third

                            val totalAmount = triple.second.sumBy { it.assetAmount }
                            val assetTypeItems = assetTypes.map {  currentAssetType ->
                                val corresAssets = assets.filter { it.assetTypeId == currentAssetType.assetTypeId }
                                val assetTypeSum = corresAssets.sumBy { it.assetAmount }
                                val assetTypePercentage = assetTypeSum.toFloat() / totalAmount
                                val targetSum = (totalAmount * (currentAssetType.targetWeight.toFloat() / 100f)).toInt()
                                val targetWeight = currentAssetType.targetWeight.toFloat() / 100

                                AssetTypeItem(
                                    assetTypeMeta = currentAssetType,
                                    assetTypeSum = assetTypeSum,
                                    assetTypePercentage = assetTypePercentage,
                                    targetSum = targetSum,
                                    targetWeight = targetWeight
                                )
                            }

                            currentAssetTypeItems.setValueSafely(assetTypeItems)

                            val varianceSum = assetTypeItems.sumByDouble {
                                (it.assetTypePercentage - it.assetTypeMeta.targetWeight.toFloat() / 100).pow(2).toDouble()
                            }

                            val currentAccountDetailItem = accounts.filter { it.accountId == selectedAccountId }.map { currentAccount ->
                                val sum = assets.filter { it.accountId == currentAccount.accountId }.sumBy { it.assetAmount }
                                val score = (1 - varianceSum.toFloat()) * 100
                                AccountDetailItem(currentAccount, sum, score.toInt())
                            }.first()

                            currentAccountLiveData.setValueSafely(currentAccountDetailItem)

                            val accountListItem = accounts.map {
                                val isSelected = it.accountId == selectedAccountId
                                AccountListItem(it, isSelected)
                            }

                            accountListLiveData.setValueSafely(accountListItem)
                        }
                }
                .subscribeOn(Schedulers.io())
                .subscribe(),

            Flowable.combineLatest(
                accountRepository.getAccountMetaList(),
                assetRepository.getAssetAllMetaList(),
                BiFunction<List<AccountMeta>, List<AssetMeta>, Pair<List<AccountMeta>, List<AssetMeta>>> {
                    a, b -> Pair(a, b)
                }
            )
                .doOnNext { pair ->
                    val accountList = pair.first
                    val assetList = pair.second

                    accountList.map { currentAccount ->
                        val sum = assetList.filter { it.accountId == currentAccount.accountId }.sumBy { it.assetAmount }
                        val isSelected = appPreference.selectedAccountIdAsValue() == currentAccount.accountId


                    }
                }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun ready() {
        compositeDisposable.add(
            accountRepository.getAccountMetaList()
                .flatMap { list ->
                    if (list.isEmpty()) {
                        createDefaultAccount()
                    } else {
                        Flowable.fromCallable {
                            list.find { appPreference.selectedAccountIdAsValue() == it.accountId }?.accountId ?: list.first().accountId
                        }
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { accountId ->
                    Logger.d("accountId: $accountId")

                    if (accountId != appPreference.selectedAccountIdAsValue()){
                        appPreference.updateSelectedAccountId(accountId)
                    }
                    isReady.setValueSafely(true)
                }
                .subscribe()
        )
    }

    fun deleteAsset(assetId: Long) {
        compositeDisposable.add(
            Single.fromCallable {
                assetRepository.delete(assetId)
            }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun deleteAssetType(assetTypeId: Long) {
        compositeDisposable.add(
            Single.fromCallable {
                assetTypeRepository.delete(assetTypeId)
            }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun changeSelectedAccountId(accountId: Long) {
        appPreference.updateSelectedAccountId(accountId)
    }

    fun upsertAssetType(assetTypeMeta: AssetTypeMeta) {
        compositeDisposable.add(
            Single.fromCallable {
                assetTypeRepository.upsert(assetTypeMeta)
            }
                .subscribeOn(Schedulers.io())
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

data class AccountDetailItem(
    val accountMeta: AccountMeta,
    val accountSum: Int,
    val score: Int
) {
    val accountId get() = accountMeta.accountId
    val accountName get() = accountMeta.accountName
}

data class AccountListItem (
    val accountMeta: AccountMeta,
    val selected: Boolean
)

data class AssetTypeItem(
    val assetTypeMeta: AssetTypeMeta,
    val assetTypeSum: Int,
    val assetTypePercentage: Float,
    val targetSum: Int,
    val targetWeight: Float
) {
}