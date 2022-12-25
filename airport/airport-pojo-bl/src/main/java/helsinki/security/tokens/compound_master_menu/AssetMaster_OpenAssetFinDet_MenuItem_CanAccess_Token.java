package helsinki.security.tokens.compound_master_menu;

import static java.lang.String.format;

import ua.com.fielden.platform.security.tokens.Template;
import helsinki.asset.master.menu.actions.AssetMaster_OpenAssetFinDet_MenuItem;
import helsinki.security.tokens.UsersAndPersonnelModuleToken;

/**
 * A security token for entity {@link AssetMaster_OpenAssetFinDet_MenuItem} to guard Access.
 *
 * @author Developers
 *
 */
public class AssetMaster_OpenAssetFinDet_MenuItem_CanAccess_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.MASTER_MENU_ITEM_ACCESS.forTitle(), AssetMaster_OpenAssetFinDet_MenuItem.ENTITY_TITLE);
    public final static String DESC = format(Template.MASTER_MENU_ITEM_ACCESS.forDesc(), AssetMaster_OpenAssetFinDet_MenuItem.ENTITY_TITLE);
}