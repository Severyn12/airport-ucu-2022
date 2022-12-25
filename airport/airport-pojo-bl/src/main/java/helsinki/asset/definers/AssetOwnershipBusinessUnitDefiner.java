package helsinki.asset.definers;

import static helsinki.asset.AssetOwnershipCo.ER_ONE_OF_OWNERSHIP_PROPS_IS_REQUIRED;
import static metamodels.MetaModels.AssetOwnership_;

import org.apache.commons.lang3.StringUtils;

import helsinki.asset.AssetOwnership;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractAfterChangeEventHandler;

public class AssetOwnershipBusinessUnitDefiner extends AbstractAfterChangeEventHandler<String> {

    @Override
    public void handle(final MetaProperty<String> mp, final String value) {
       final AssetOwnership ao = mp.getEntity();
       
       if (ao.isInitialising()) {
           mp.setRequired(!StringUtils.isEmpty(value), ER_ONE_OF_OWNERSHIP_PROPS_IS_REQUIRED);

           
       }else {
           if(!StringUtils.isEmpty(value)) {
               mp.setRequired(true, ER_ONE_OF_OWNERSHIP_PROPS_IS_REQUIRED);
               ao.getProperty(AssetOwnership_.role()).setRequired(false, ER_ONE_OF_OWNERSHIP_PROPS_IS_REQUIRED);
               ao.getProperty(AssetOwnership_.organisation()).setRequired(false, ER_ONE_OF_OWNERSHIP_PROPS_IS_REQUIRED);
               
               ao.setRole(null);
               ao.setOrganisation(null);

           }
       }
    }

}
