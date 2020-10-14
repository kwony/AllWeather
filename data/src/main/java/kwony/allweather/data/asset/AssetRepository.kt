package kwony.allweather.data.asset

import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetRepository @Inject constructor() {
    @Inject lateinit var assetDao: AssetDao

    fun getAssetMetaList(accountId: Long): Flowable<List<AssetMeta>> {
        return assetDao.getEntitiesFromAccount(accountId)
    }

    fun createAssetMeta(assetMeta: AssetMeta): Long {
        return assetDao.upsert(assetMeta)
    }
}