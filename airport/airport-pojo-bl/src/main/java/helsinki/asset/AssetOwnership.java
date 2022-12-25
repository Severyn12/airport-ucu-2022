package helsinki.asset;

import static helsinki.asset.AssetOwnershipCo.ERR_PERSISTED_ASSET_OWNERSHIP_CANNOT_BE_CHANGED;

import java.util.Date;

import helsinki.asset.definers.AssetOwnershipBusinessUnitDefiner;
import helsinki.asset.definers.AssetOwnershipOrganisationDefiner;
import helsinki.asset.definers.AssetOwnershipRoleDefiner;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.mutator.AfterChange;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * One-2-Many entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Asset Ownership")
@CompanionObject(AssetOwnershipCo.class)
@MapEntityTo
public class AssetOwnership extends AbstractPersistentEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(AssetOwnership.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

    @IsProperty
    @MapTo
    @CompositeKeyMember(1)
    private Asset asset;

    @IsProperty
    @MapTo
    @Title(value = "Start date", desc = "The date when this ownership started.")
    @CompositeKeyMember(2)
    private Date startDate;

    @IsProperty
    @MapTo
    @Title(value = "Role", desc = "Role as owner")
    @AfterChange(AssetOwnershipRoleDefiner.class)
    private String role;

    @IsProperty
    @MapTo
    @Title(value = "Business unit", desc = "Business unit as owner")
    @AfterChange(AssetOwnershipBusinessUnitDefiner.class)
    private String businessUnit;

    @IsProperty
    @MapTo
    @Title(value = "Organisation", desc = "Organisation as owner")
    @AfterChange(AssetOwnershipOrganisationDefiner.class)
    private String organisation;

    @Override
    public Result isEditable() {
        final var res = super.isEditable();
        
        if (!res.isSuccessful()) {
            return res;
        }
        if (isPersisted()) {
            return Result.failure(ERR_PERSISTED_ASSET_OWNERSHIP_CANNOT_BE_CHANGED);
        }
        return res;
    }
    
    @Observable
    public AssetOwnership setOrganisation(final String organisation) {
        this.organisation = organisation;
        return this;
    }

    public String getOrganisation() {
        return organisation;
    }

    @Observable
    public AssetOwnership setBusinessUnit(final String businessUnit) {
        this.businessUnit = businessUnit;
        return this;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }
    
    @Observable
    public AssetOwnership setRole(final String role) {
        this.role = role;
        return this;
    }

    public String getRole() {
        return role;
    }

    @Observable
    public AssetOwnership setStartDate(final Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    @Observable 
    public AssetOwnership setAsset(final Asset value) {
        this.asset = value;
        return this;
    }

    public Asset getAsset() {
        return asset;
    }

}
