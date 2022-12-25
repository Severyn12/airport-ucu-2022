package helsinki.asset.producers;

import com.google.inject.Inject;

import helsinki.asset.Asset;
import helsinki.asset.AssetOwnership;
import ua.com.fielden.platform.entity.DefaultEntityProducerWithContext;
import ua.com.fielden.platform.entity.EntityNewAction;
import ua.com.fielden.platform.entity.factory.EntityFactory;
import ua.com.fielden.platform.entity.factory.ICompanionObjectFinder;
import ua.com.fielden.platform.error.Result;
/**
 * A producer for new instances of entity {@link AssetOwnership}.
 *
 * @author Developers
 *
 */
public class AssetOwnershipProducer extends DefaultEntityProducerWithContext<AssetOwnership> {

    @Inject
    public AssetOwnershipProducer(final EntityFactory factory, final ICompanionObjectFinder coFinder) {
        super(factory, AssetOwnership.class, coFinder);
    }

    @Override
    protected AssetOwnership provideDefaultValuesForStandardNew(final AssetOwnership entityIn, final EntityNewAction masterEntity) {
        final AssetOwnership entityOut = super.provideDefaultValuesForStandardNew(entityIn, masterEntity);
        // This producer can be invoked from two places:
        // 1. Standalone centre
        // 2. Centre embedded in Asset Master
        // In the second case we want to default the asset and make it read-only
        if (ofMasterEntity().keyOfMasterEntityInstanceOf(Asset.class)) {
            final Asset shallowAsset = ofMasterEntity().keyOfMasterEntity(Asset.class);
            // shallowAsset has been fetched in OpenAssetMasterActionProducer with key and desc only
            // It needs to be re-fetched here using a slightly deeper fetch model, as appropriate for CocEntry
            entityOut.setAsset(refetch(shallowAsset, "asset"));
            entityOut.getProperty("asset").validationResult().ifFailure(Result::throwRuntime);
            entityOut.getProperty("asset").setEditable(false);
        }
        return entityOut;
    }
}
