package helsinki.asset;

import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Readonly;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.mutator.BeforeChange;
import ua.com.fielden.platform.entity.annotation.mutator.Handler;
import ua.com.fielden.platform.entity.validation.MaxLengthValidator;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Asset Number")
@CompanionObject(AssetCo.class)
@MapEntityTo
@DescTitle("Asset Description")
@DisplayDescription
@DescRequired

public class Asset extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Asset.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    public static final int ASSET_NO_LENGTH = 6;
    
    @IsProperty(length=ASSET_NO_LENGTH)
    @MapTo
    @Title(value = "Number", desc = "Auto-generated unique asset number.")
    @CompositeKeyMember(1)
    @Readonly
    @BeforeChange(@Handler(MaxLengthValidator.class))
    private String number;
    
    @IsProperty
    @MapTo
    @Required
    @Title(value = "AssetType", desc = "Asset type property")
    private AssetType assetType;

    @Observable
    public Asset setAssetType(final AssetType assetType) {
        this.assetType = assetType;
        return this;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    


    @Observable
    public Asset setNumber(final String number) {
        this.number = number;
        return this;
    }

    public String getNumber() {
        return number;
    }

    


}
