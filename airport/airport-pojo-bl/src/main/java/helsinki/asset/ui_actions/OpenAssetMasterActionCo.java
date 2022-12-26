package helsinki.asset.ui_actions;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link OpenAssetMasterAction}.
 *
 * @author Developers
 *
 */
public interface OpenAssetMasterActionCo extends IEntityDao<OpenAssetMasterAction> {

    static final IFetchProvider<OpenAssetMasterAction> FETCH_PROVIDER = EntityUtils.fetch(OpenAssetMasterAction.class).with(
        // key is needed to be correctly autopopulated by newly saved compound master entity (ID-based restoration of entity-typed key)
        "key");

}
