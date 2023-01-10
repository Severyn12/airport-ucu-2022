package helsinki.asset.definers;


import static metamodels.MetaModels.AssetFinDet_;

import java.util.Date;

import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractAfterChangeEventHandler;

public class MakeInitCostRequiredDefiner extends AbstractAfterChangeEventHandler<Date> {

    @Override
    public void handle(final MetaProperty<Date> mpComissionDate, final Date value) {
        mpComissionDate.getEntity().getProperty(AssetFinDet_.initCost()).setRequired(null != value);        
    }

}