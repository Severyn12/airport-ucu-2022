package helsinki.asset.master.menu.actions;

import com.google.inject.Inject;

import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.compound_master_menu.AssetMaster_OpenAssetOwnership_MenuItem_CanAccess_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link AssetMaster_OpenAssetOwnership_MenuItemCo}.
 *
 * @author Developers
 *
 */
@EntityType(AssetMaster_OpenAssetOwnership_MenuItem.class)
public class AssetMaster_OpenAssetOwnership_MenuItemDao extends CommonEntityDao<AssetMaster_OpenAssetOwnership_MenuItem> implements AssetMaster_OpenAssetOwnership_MenuItemCo {

    @Inject
    public AssetMaster_OpenAssetOwnership_MenuItemDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(AssetMaster_OpenAssetOwnership_MenuItem_CanAccess_Token.class)
    public AssetMaster_OpenAssetOwnership_MenuItem save(AssetMaster_OpenAssetOwnership_MenuItem entity) {
        return super.save(entity);
    }

}