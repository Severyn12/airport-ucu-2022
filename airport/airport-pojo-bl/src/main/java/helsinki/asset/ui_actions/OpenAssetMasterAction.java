package helsinki.asset.ui_actions;

import helsinki.asset.Asset;
import helsinki.asset.AssetFinDet;
import ua.com.fielden.platform.entity.AbstractFunctionalEntityToOpenCompoundMaster;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.EntityTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Open Master Action entity object.
 *
 * @author Developers
 *
 */
@KeyType(Asset.class)
@CompanionObject(OpenAssetMasterActionCo.class)
@EntityTitle("Asset Master")
public class OpenAssetMasterAction extends AbstractFunctionalEntityToOpenCompoundMaster<Asset> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(OpenAssetMasterAction.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

    public static final String MAIN = "Main";
    public static final String ASSETFINDETS = AssetFinDet.ENTITY_TITLE + "s"; // Please adjust manually if the plural form is not standard
}
