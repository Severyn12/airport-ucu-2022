package helsinki.asset;

import java.util.Date;

import helsinki.asset.meta.AssetFinDetMetaModel;
import ua.com.fielden.platform.entity.AbstractEntity;
import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.Dependent;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.validation.annotation.GeProperty;
import ua.com.fielden.platform.entity.validation.annotation.LeProperty;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.utils.Pair;

/**
 * One-2-One entity object.
 *
 * @author Developers
 *
 */
@KeyType(Asset.class)
@KeyTitle("Asset")
@CompanionObject(AssetFinDetCo.class)
@MapEntityTo
public class AssetFinDet extends AbstractPersistentEntity<Asset> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(AssetFinDet.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

    @IsProperty
    @MapTo
    @Title(value = "Initial Cost ($)", desc = "The initial cost of this asset")
    private Money initCost;
    
    @IsProperty
    @MapTo
    @Dependent(AssetFinDetMetaModel.disposalDate_)
    @Title(value = "Comission Date", desc = "The date when this asset was comissioned.")
    private Date comissionDate;

    @IsProperty
    @MapTo
    @Dependent(AssetFinDetMetaModel.comissionDate_)
    @Title(value = "Desposal Date", desc = "The date when this asset was desposed of.")
    private Date disposalDate;
    
    @Observable
    @Override
        public AssetFinDet setKey(final Asset key) {
            super.setKey(key);
            return this;
        }
 
    @Observable
    @LeProperty(AssetFinDetMetaModel.disposalDate_)
    public AssetFinDet setComissionDate(final Date comissionDate) {
        this.comissionDate = comissionDate;
        return this;
    }

    public Date getComissionDate() {
        return comissionDate;
    }

    @Observable
    @GeProperty(AssetFinDetMetaModel.comissionDate_)
    public AssetFinDet setDisposalDate(final Date disposalDate) {
        this.disposalDate = disposalDate;
        return this;
    }

    public Date getDisposalDate() {
        return disposalDate;
    }

    @Observable
    public AssetFinDet setInitCost(final Money initCost) {
        this.initCost = initCost;
        return this;
    }

    public Money getInitCost() {
        return initCost;
    }

    

    
}