package helsinki.asset;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;

import static metamodels.MetaModels.AssetOwnership_;

import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link AssetOwnership}.
 *
 * @author Developers
 *
 */
public interface AssetOwnershipCo extends IEntityDao<AssetOwnership> {
    
    final String ER_ONE_OF_OWNERSHIP_PROPS_IS_REQUIRED = "One of the ownerships should be spiciefied";
    final String ERR_PERSISTED_ASSET_OWNERSHIP_CANNOT_BE_CHANGED = "Asset ownership cannot be changed after being saved. Please create a new one and delete the currect if required";

    static final IFetchProvider<AssetOwnership> FETCH_PROVIDER = EntityUtils.fetch(AssetOwnership.class)
            .with(AssetOwnership_.asset(), AssetOwnership_.startDate(), AssetOwnership_.role(), AssetOwnership_.businessUnit(), AssetOwnership_.organisation());

}
