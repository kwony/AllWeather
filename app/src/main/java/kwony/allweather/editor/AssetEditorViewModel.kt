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
import kwony.allweather.data.asset.AssetMeta
import kwony.allweather.data.asset.AssetRepository
import kwony.allweather.data.asset.AssetTypeMeta
import kwony.allweather.data.asset.AssetTypeRepository
import javax.inject.Inject

class AssetEditorViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    application: Application
): AndroidViewModel(application) {
    @Inject lateinit var assetRepository: AssetRepository

    @Inject lateinit var assetTypeRepository: AssetTypeRepository

    val assetTypes: MutableLiveData<List<AssetTypeMeta>> = MutableLiveData()

    val editingAssetMeta: MutableLiveData<AssetMeta> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    private var accountId: Long = -1L
    private var creationMode: Boolean = true

    fun init(accountId: Long, creationMode: Boolean, assetId: Long = 0L) {
        this.accountId = accountId
        this.creationMode = creationMode

        if (!creationMode) {
            compositeDisposable.add(
                assetRepository.getAssetMeta(assetId)
                    .doOnNext { editingAssetMeta.value = it }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            )
        }

        compositeDisposable.add(
            assetTypeRepository.getAssetTypeMetaList(accountId)
                .doOnNext { assetTypes.value = it }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun done(name: String, typeId: Long, amount: Int) {
        require(accountId >= 0)

        val assetMeta = AssetMeta(
            assetId = if (creationMode) -1L else editingAssetMeta.value!!.assetId,
            accountId = accountId,
            assetName = name,
            assetAmount = amount,
            assetTypeId = typeId
        )

        compositeDisposable.add(
            Single.fromCallable {
                assetRepository.upsert(assetMeta)
            }
                .subscribeOn(Schedulers.io())
                .subscribe()
        )

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}