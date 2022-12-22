package helsinki.asset;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;

import static metamodels.MetaModels.Asset_;

import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Asset}.
 *
 * @author Developers
 *
 */
public interface AssetCo extends IEntityDao<Asset> {

    static final IFetchProvider<Asset> FETCH_PROVIDER = EntityUtils.fetch(Asset.class)
            .with(Asset_.number(), Asset_.desc(), Asset_.assetType());
        // TODO: uncomment the following line and specify the properties, which are required for the UI. Then remove the line after.
        // "key", "desc");
 
}
