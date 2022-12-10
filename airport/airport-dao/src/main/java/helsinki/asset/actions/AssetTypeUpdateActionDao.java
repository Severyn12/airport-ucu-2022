package helsinki.asset.actions;

import static metamodels.MetaModels.AssetType_;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.cond;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.expr;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAggregates;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAll;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAllAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAllInclCalc;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAllInclCalcAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchIdOnly;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchKeyAndDescOnly;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchKeyAndDescOnlyAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchOnly;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchOnlyAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.from;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.orderBy;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;
import static ua.com.fielden.platform.utils.EntityUtils.fetch;

import java.util.Optional;

import com.google.inject.Inject;

import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.asset.AssetType;
import helsinki.asset.AssetTypeCo;
import helsinki.security.tokens.functional.AssetTypeUpdateAction_CanExecute_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.dao.QueryExecutionModel;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.query.fluent.fetch;
import ua.com.fielden.platform.entity.query.model.EntityResultQueryModel;
import ua.com.fielden.platform.entity.query.model.OrderingModel;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;

/**
 * DAO implementation for companion object {@link AssetTypeUpdateActionCo}.
 *
 * @author Developers
 *
 */
@EntityType(AssetTypeUpdateAction.class)
public class AssetTypeUpdateActionDao extends CommonEntityDao<AssetTypeUpdateAction> implements AssetTypeUpdateActionCo {

    @Inject
    public AssetTypeUpdateActionDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(AssetTypeUpdateAction_CanExecute_Token.class)
    public AssetTypeUpdateAction save(final AssetTypeUpdateAction action) {
        // retrieve all AssetType instances, specified in action
        // set their value of property AssetClass to the action's assetClass
        // save the updated AssetType instances
        
        //it is a good practice to check whether action is valid
        action.isValid().ifFailure(Result::throwRuntime);
        
        if ( !action.getSelectedAssetTypeIds().isEmpty() ) {
            final AssetTypeCo co$ = co$(AssetType.class); 
            final EntityResultQueryModel<AssetType> query = select(AssetType.class).where().prop(AssetType_.id()).in().values(action.getSelectedAssetTypeIds().toArray()).model();
           
            for (final AssetType at: co$.getAllEntities(from(query).with(AssetTypeCo.FETCH_PROVIDER.fetchModel()).model())) {
                co$.save(at.setAssetClass(action.getAssetClass()));
            }
        }
        
        int amount = action.getSelectedAssetTypeIds().size();
        String notification = "";
        
        if (amount == 1) {
            notification = "Updated 1 asset type";
        }
        else {
            notification = "Updated %s asset type".formatted(amount);
        }
        
        action.setPostActionMessage(notification);
        
        return super.save(action);
    }
    @Override
    protected IFetchProvider<AssetTypeUpdateAction> createFetchProvider() {
        return FETCH_PROVIDER;
    }

}