package helsinki.asset.master.menu.actions;

import helsinki.asset.Asset;
import ua.com.fielden.platform.entity.AbstractFunctionalEntityForCompoundMenuItem;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.EntityTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object to model the detail menu item of the compound master entity object.
 *
 * @author Developers
 *
 */
@KeyType(Asset.class)
@CompanionObject(AssetMaster_OpenAssetOwnership_MenuItemCo.class)
@EntityTitle("Asset Master Asset Ownership Menu Item")
public class AssetMaster_OpenAssetOwnership_MenuItem extends AbstractFunctionalEntityForCompoundMenuItem<Asset> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(AssetMaster_OpenAssetOwnership_MenuItem.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

}
