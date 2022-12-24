package helsinki.asset;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;

import static metamodels.MetaModels.AssetFinDet_;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link AssetFinDet}.
 *
 * @author Developers
 *
 */
public interface AssetFinDetCo extends IEntityDao<AssetFinDet> {

    static final IFetchProvider<AssetFinDet> FETCH_PROVIDER = EntityUtils.fetch(AssetFinDet.class)
            .with(AssetFinDet_.key(), AssetFinDet_.initCost(), AssetFinDet_.comissionDate(), AssetFinDet_.disposalDate());
}
