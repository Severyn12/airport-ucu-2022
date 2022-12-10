package helsinki.asset.actions.producers;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.DefaultEntityProducerWithContext;
import ua.com.fielden.platform.entity.factory.EntityFactory;
import ua.com.fielden.platform.entity.factory.ICompanionObjectFinder;
import ua.com.fielden.platform.error.Result;
import helsinki.asset.actions.AssetTypeUpdateAction;
/**
 * A producer for new instances of entity {@link AssetTypeUpdateAction}.
 *
 * @author Developers
 *
 */
public class AssetTypeUpdateActionProducer extends DefaultEntityProducerWithContext<AssetTypeUpdateAction> {

    @Inject
    public AssetTypeUpdateActionProducer(final EntityFactory factory, final ICompanionObjectFinder coFinder) {
        super(factory, AssetTypeUpdateAction.class, coFinder);
    }

    @Override
    protected AssetTypeUpdateAction provideDefaultValues(final AssetTypeUpdateAction action) {
       
        if ( selectedEntitiesNotEmpty() ) {
            action.setSelectedAssetTypeIds(selectedEntityIds());
        }
        else {
            throw Result.failure("Please select at least one asset type.");
        }
        return action;
    }
}
