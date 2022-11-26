package helsinki.asset.actions;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import helsinki.asset.AssetClass;
import ua.com.fielden.platform.entity.AbstractFunctionalEntityWithCentreContext;
import ua.com.fielden.platform.entity.NoKey;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(NoKey.class)
@KeyTitle("Key")
@CompanionObject(AssetTypeUpdateActionCo.class)
public class AssetTypeUpdateAction extends AbstractFunctionalEntityWithCentreContext<NoKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(AssetTypeUpdateAction.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

    public AssetTypeUpdateAction() {
        setKey(NoKey.NO_KEY);
    }
    
    @IsProperty
    @Required
    @Title(value = "Asset Class", desc = "Asset class to which selected asset types are going to be updated")
    private AssetClass assetClass;

    @IsProperty(Long.class)
    @Title(value = "Selected Asset Type Ids", desc = "Extended_description")
    private final Set<Long> selectedAssetTypeIds = new LinkedHashSet<Long>();

    @Observable
    protected AssetTypeUpdateAction setSelectedAssetTypeIds(final Set<Long> selectedAssetTypeIds) {
        this.selectedAssetTypeIds.clear();
        this.selectedAssetTypeIds.addAll(selectedAssetTypeIds);
        return this;
    }

    public Set<Long> getSelectedAssetTypeIds() {
        return Collections.unmodifiableSet(selectedAssetTypeIds);
    }
    
    @Observable
    public AssetTypeUpdateAction setAssetClass(final AssetClass assetClass) {
        this.assetClass = assetClass;
        return this;
    }

    public AssetClass getAssetClass() {
        return assetClass;
    }

    

}
