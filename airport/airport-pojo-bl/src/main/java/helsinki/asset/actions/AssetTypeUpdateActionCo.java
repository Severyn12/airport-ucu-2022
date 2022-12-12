package helsinki.asset.actions;

import static metamodels.MetaModels.AssetTypeUpdateAction_;

import ua.com.fielden.platform.dao.IEntityDao;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;

/**
 * Companion object for entity {@link AssetTypeUpdateAction}.
 *
 * @author Developers
 *
 */
public interface AssetTypeUpdateActionCo extends IEntityDao<AssetTypeUpdateAction> {
    
    static final IFetchProvider<AssetTypeUpdateAction> FETCH_PROVIDER = EntityUtils.fetch(AssetTypeUpdateAction.class)
            .with(AssetTypeUpdateAction_.assetClass());

}
