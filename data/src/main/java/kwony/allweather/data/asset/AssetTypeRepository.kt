package kwony.allweather.data.asset

import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

class AssetTypeRepository(private val assetTypeDao: AssetTypeDao) {
    fun getAllAssetTypeList(): Flowable<List<AssetTypeMeta>> {
        return assetTypeDao.getEntities()
    }

    fun getAssetTypeMetaList(accountId: Long): Flowable<List<AssetTypeMeta>> {
        return assetTypeDao.getEntitiesFromAccount(accountId)
    }

    fun getAssetTypeMeta(assetTypeId: Long): Flowable<AssetTypeMeta> {
        return assetTypeDao.getEntity(assetTypeId)
    }

    fun upsert(assetTypeMeta: AssetTypeMeta): Long {
        return assetTypeDao.upsert(assetTypeMeta)
    }

    fun upsert(assetTypeMeta: List<AssetTypeMeta>): List<Long> {
        return assetTypeDao.upsert(assetTypeMeta)
    }

    fun deleteByAccountId(accountId: Long) {
        return assetTypeDao.deleteByAccountId(accountId)
    }

    fun delete(assetTypeId: Long) {
        return assetTypeDao.delete(assetTypeId)
    }
}
