package helsinki.asset;

import static metamodels.MetaModels.AssetType_;
import ua.com.fielden.platform.dao.IEntityDao;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;

/**
 * Companion object for entity {@link AssetType}.
 *
 * @author Developers
 *
 */
public interface AssetTypeCo extends IEntityDao<AssetType> {

    static final IFetchProvider<AssetType> FETCH_PROVIDER = EntityUtils.fetch(AssetType.class)
            .with(AssetType_.name(), AssetType_.desc(), AssetType_.active(), AssetType_.assetClass());

}
