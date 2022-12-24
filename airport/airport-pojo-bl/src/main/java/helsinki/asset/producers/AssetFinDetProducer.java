package helsinki.asset.producers;

import com.google.inject.Inject;

import helsinki.asset.AssetFinDet;
import helsinki.asset.AssetFinDetCo;
import helsinki.asset.Asset;
import ua.com.fielden.platform.entity.DefaultEntityProducerWithContext;
import ua.com.fielden.platform.entity.factory.EntityFactory;
import ua.com.fielden.platform.entity.factory.ICompanionObjectFinder;
import ua.com.fielden.platform.error.Result;
/**
 * A producer for new instances of entity {@link AssetFinDet}.
 *
 * @author Developers
 *
 */
public class AssetFinDetProducer extends DefaultEntityProducerWithContext<AssetFinDet> {

    @Inject
    public AssetFinDetProducer(final EntityFactory factory, final ICompanionObjectFinder coFinder) {
        super(factory, AssetFinDet.class, coFinder);
    }

    @Override
    protected AssetFinDet provideDefaultValues(final AssetFinDet entity) {
        if (keyOfMasterEntityInstanceOf(Asset.class)) {
            final Asset instance = keyOfMasterEntity(Asset.class);
            return co$(AssetFinDet.class).findByKeyAndFetch(AssetFinDetCo.FETCH_PROVIDER.fetchModel(), instance);
        } else {
            throw new Result("Not supported.");
        }
    }
}
