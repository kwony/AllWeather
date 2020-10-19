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
import kwony.allweather.arch.setValueSafely
import kwony.allweather.data.asset.AssetMeta
import kwony.allweather.data.asset.AssetRepository
import kwony.allweather.data.asset.AssetTypeMeta
import kwony.allweather.data.asset.AssetTypeRepository
import javax.inject.Inject

class AssetEditorViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application,
    private val assetRepository: AssetRepository,
    private val assetTypeRepository: AssetTypeRepository
): AndroidViewModel(application) {
    val assetTypes: MutableLiveData<List<AssetTypeMeta>> = MutableLiveData()

    val editingAssetMeta: MutableLiveData<AssetMeta> = MutableLiveData()

    val done: MutableLiveData<Boolean> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    private var accountId: Long = -1L
    private var creationMode: Boolean = true

    fun init(accountId: Long, creationMode: Boolean, assetId: Long = 0L) {
        this.accountId = accountId
        this.creationMode = creationMode

        if (!creationMode) {
            compositeDisposable.add(
                assetRepository.getAssetMeta(assetId)
                    .doOnNext { editingAssetMeta.setValueSafely(it) }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            )
        }

        compositeDisposable.add(
            assetTypeRepository.getAssetTypeMetaList(accountId)
                .doOnNext { assetTypes.setValueSafely(it) }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun done(name: String, typeId: Long, amount: Int) {
        require(accountId >= 0)

        val assetMeta = AssetMeta(
            assetId = if (creationMode) 0L else editingAssetMeta.value!!.assetId,
            accountId = accountId,
            assetName = name,
            assetAmount = amount,
            assetTypeId = typeId
        )

        compositeDisposable.add(
            Single.fromCallable {
                assetRepository.upsert(assetMeta)
            }
                .doOnSuccess { done.setValueSafely(true) }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}