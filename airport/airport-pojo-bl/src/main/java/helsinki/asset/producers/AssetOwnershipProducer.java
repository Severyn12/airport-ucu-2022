package helsinki.asset.producers;

import static metamodels.MetaModels.AssetOwnership_;

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
    protected AssetOwnership provideDefaultValuesForStandardNew(final AssetOwnership assetOwnershipIn, final EntityNewAction masterEntity) {
        final AssetOwnership assetOwnershipOut = super.provideDefaultValuesForStandardNew(assetOwnershipIn, masterEntity);
        // This producer can be invoked from two places:
        // 1. Standalone centre -- not in our case, as we do not have a standalone centre for AssetOwnership
        // 2. Centre embedded in Asset Master
        // In the second case we want to default the asset and make it read-only
        if (ofMasterEntity().keyOfMasterEntityInstanceOf(Asset.class)) {
            final Asset shallowAsset = ofMasterEntity().keyOfMasterEntity(Asset.class);
            // shallowAsset has been fetched in OpenAssetMasterActionProducer with key and desc only
            // It needs to be re-fetched here using a slightly deeper fetch model, as appropriate for AssetOwnership
            assetOwnershipOut.setAsset(refetch(shallowAsset, AssetOwnership_.asset()));
            assetOwnershipOut.getProperty(AssetOwnership_.asset()).validationResult().ifFailure(Result::throwRuntime);
            assetOwnershipOut.getProperty(AssetOwnership_.asset()).setEditable(false);
        }
        return assetOwnershipOut;
    }
}
