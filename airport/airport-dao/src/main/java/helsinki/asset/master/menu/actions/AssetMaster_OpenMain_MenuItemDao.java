package helsinki.asset.master.menu.actions;

import com.google.inject.Inject;

import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.compound_master_menu.AssetMaster_OpenMain_MenuItem_CanAccess_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link AssetMaster_OpenMain_MenuItemCo}.
 *
 * @author Developers
 *
 */
@EntityType(AssetMaster_OpenMain_MenuItem.class)
public class AssetMaster_OpenMain_MenuItemDao extends CommonEntityDao<AssetMaster_OpenMain_MenuItem> implements AssetMaster_OpenMain_MenuItemCo {

    @Inject
    public AssetMaster_OpenMain_MenuItemDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(AssetMaster_OpenMain_MenuItem_CanAccess_Token.class)
    public AssetMaster_OpenMain_MenuItem save(AssetMaster_OpenMain_MenuItem entity) {
        return super.save(entity);
    }

}