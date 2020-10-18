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
import kwony.allweather.data.account.AccountMeta
import kwony.allweather.data.account.AccountRepository
import kwony.allweather.data.asset.AssetTypeMeta
import kwony.allweather.data.asset.AssetTypeRepository
import javax.inject.Inject

class AccountEditorViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application
): AndroidViewModel(application) {

    @Inject lateinit var accountRepository: AccountRepository

    @Inject lateinit var assetTypeRepository: AssetTypeRepository

    val editingAccountMeta: MutableLiveData<AccountMeta> = MutableLiveData()

    val assetTypes: MutableLiveData<ArrayList<AssetTypeMeta>> = MutableLiveData()

    private var creationMode: Boolean = false

    private val compositeDisposable = CompositeDisposable()

    fun init(creationMode: Boolean, accountId: Long) {
        this.creationMode = creationMode

        if (!creationMode) {
            compositeDisposable.add(
                accountRepository.getAccountMeta(accountId)
                    .doOnNext { editingAccountMeta.value = it }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            )
        }

        compositeDisposable.add(
            assetTypeRepository.getAssetTypeMetaList(accountId)
                .doOnNext { assetTypes.value = ArrayList<AssetTypeMeta>().apply { addAll(it) } }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun done(name: String) {
        compositeDisposable.add(
            Single.fromCallable {
                val accountMeta = AccountMeta(
                    accountId = if (creationMode) 0L else editingAccountMeta.value!!.accountId,
                    accountName = name
                )
                accountRepository.upsert(accountMeta)
            }
                .doOnSuccess {
                    // todo: 자산 배분 정도 이제 업데이트 해주기

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