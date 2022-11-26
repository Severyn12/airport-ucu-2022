package helsinki.asset.actions;

import java.util.Optional;

import com.google.inject.Inject;

import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.asset.AssetType;
import helsinki.asset.AssetTypeCo;
import helsinki.security.tokens.functional.AssetTypeUpdateAction_CanExecute_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

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
        final AssetTypeCo co$ = co$(AssetType.class);
        for (final Long id: action.getSelectedAssetTypeIds()) {
            final Optional<AssetType> maybeAssetType = co$.findByIdOptional(id, AssetTypeCo.FETCH_PROVIDER.fetchModel());
            if (maybeAssetType.isPresent()) {
                final AssetType at = maybeAssetType.get().setAssetClass(action.getAssetClass());
                co$.save(at);
            }
        }
        return super.save(action);
    }

}