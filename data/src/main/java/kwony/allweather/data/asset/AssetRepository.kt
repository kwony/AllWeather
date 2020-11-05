package kwony.allweather.data.asset

import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

class AssetRepository(private val assetDao: AssetDao) {
    fun getAssetMeta(assetId: Long): Flowable<AssetMeta> {
        return assetDao.getEntity(assetId)
    }

    fun getAssetAllMetaList(): Flowable<List<AssetMeta>> {
        return assetDao.getEntities()
    }

    fun getAssetMetaList(accountId: Long): Flowable<List<AssetMeta>> {
        return assetDao.getEntitiesFromAccount(accountId)
    }

    fun upsert(assetMeta: AssetMeta): Long {
        return assetDao.upsert(assetMeta)
    }

    fun delete(assetId: Long) {
        return assetDao.delete(assetId)
    }
}