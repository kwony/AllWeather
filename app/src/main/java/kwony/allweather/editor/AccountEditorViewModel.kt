package kwony.allweather.editor

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kwony.allweather.arch.notifyChange
import kwony.allweather.arch.setValueSafely
import kwony.allweather.data.account.AccountMeta
import kwony.allweather.data.account.AccountRepository
import kwony.allweather.data.asset.AssetTypeMeta
import kwony.allweather.data.asset.AssetTypeRepository
import javax.inject.Inject

class AccountEditorViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val accountRepository: AccountRepository,
    private val assetTypeRepository: AssetTypeRepository,
    application: Application
): AndroidViewModel(application) {

    val editingAccountMeta: MutableLiveData<AccountMeta> = MutableLiveData()

    val assetTypes: MutableLiveData<ArrayList<AssetTypeMeta>> = MutableLiveData(ArrayList())

    private var creationMode: Boolean = false

    private val compositeDisposable = CompositeDisposable()

    fun init(creationMode: Boolean, accountId: Long) {
        this.creationMode = creationMode

        if (!creationMode) {
            compositeDisposable.add(
                accountRepository.getAccountMeta(accountId)
                    .doOnNext { editingAccountMeta.setValueSafely(it) }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            )
        }

        compositeDisposable.add(
            assetTypeRepository.getAssetTypeMetaList(accountId)
                .doOnNext { assetTypes.setValueSafely(ArrayList<AssetTypeMeta>().apply { addAll(it) }) }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun done(name: String) {
        compositeDisposable.add(
            Single.fromCallable {
                val accountMeta = AccountMeta(
                    accountId = if (creationMode) 0L else editingAccountMeta.value!!.accountId,
                    accountName = name,
                    isDefault = false
                )
                accountRepository.upsert(accountMeta)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSuccess { accountId ->
                    assetTypes.value?.forEach {
                        it.accountId = accountId
                    }
                    assetTypes.value?.run {
                        assetTypeRepository.upsert(this)
                    }
                }
                .subscribe()
        )
    }

    fun addAssetType(assetTypeMeta: AssetTypeMeta) {
        assetTypeMeta.accountId = if (creationMode) 0L else editingAccountMeta.value!!.accountId

        assetTypes.value?.add(assetTypeMeta)
        assetTypes.notifyChange()
    }

    fun editAssetType(assetTypeMeta: AssetTypeMeta) {
        assetTypes.value?.find { it.assetTypeId == assetTypeMeta.assetTypeId }?.apply {
            this.targetWeight = assetTypeMeta.targetWeight
            this.assetTypeName = assetTypeMeta.assetTypeName
        }

        assetTypes.notifyChange()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}