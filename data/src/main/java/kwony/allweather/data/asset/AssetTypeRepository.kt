package kwony.allweather.data.asset

import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetTypeRepository @Inject constructor() {
    @Inject lateinit var assetTypeDao: AssetTypeDao

    fun getAssetTypeMetaList(accountId: Long): Flowable<List<AssetTypeMeta>> {
        return assetTypeDao.getEntitiesFromAccount(accountId)
    }

    fun createAssetType(assetTypeMeta: AssetTypeMeta): Long {
        return assetTypeDao.upsert(assetTypeMeta)
    }

    fun deleteByAccountId(accountId: Long) {
        return assetTypeDao.deleteByAccountId(accountId)
    }
}
