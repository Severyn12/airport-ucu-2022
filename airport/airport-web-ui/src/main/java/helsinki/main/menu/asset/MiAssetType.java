package helsinki.main.menu.asset;

import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.ui.menu.MiWithConfigurationSupport;
import helsinki.asset.AssetType;
/**
 * Main menu item representing an entity centre for {@link AssetType}.
 *
 * @author Developers
 *
 */
@EntityType(AssetType.class)
public class MiAssetType extends MiWithConfigurationSupport<AssetType> {

}
