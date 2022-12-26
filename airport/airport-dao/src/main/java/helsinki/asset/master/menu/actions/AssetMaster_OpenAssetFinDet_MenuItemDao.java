package helsinki.asset.master.menu.actions;

import com.google.inject.Inject;

import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.compound_master_menu.AssetMaster_OpenAssetFinDet_MenuItem_CanAccess_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link AssetMaster_OpenAssetFinDet_MenuItemCo}.
 *
 * @author Developers
 *
 */
@EntityType(AssetMaster_OpenAssetFinDet_MenuItem.class)
public class AssetMaster_OpenAssetFinDet_MenuItemDao extends CommonEntityDao<AssetMaster_OpenAssetFinDet_MenuItem> implements AssetMaster_OpenAssetFinDet_MenuItemCo {

    @Inject
    public AssetMaster_OpenAssetFinDet_MenuItemDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(AssetMaster_OpenAssetFinDet_MenuItem_CanAccess_Token.class)
    public AssetMaster_OpenAssetFinDet_MenuItem save(AssetMaster_OpenAssetFinDet_MenuItem entity) {
        return super.save(entity);
    }

}