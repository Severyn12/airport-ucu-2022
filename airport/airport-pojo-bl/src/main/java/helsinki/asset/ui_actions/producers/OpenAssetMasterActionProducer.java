package helsinki.asset.ui_actions.producers;

import com.google.inject.Inject;

import helsinki.asset.Asset;
import helsinki.asset.ui_actions.OpenAssetMasterAction;
import ua.com.fielden.platform.security.Authorise;
import helsinki.security.tokens.open_compound_master.OpenAssetMasterAction_CanOpen_Token;
import ua.com.fielden.platform.entity.AbstractProducerForOpenEntityMasterAction;
import ua.com.fielden.platform.entity.factory.EntityFactory;
import ua.com.fielden.platform.entity.factory.ICompanionObjectFinder;

/**
 * A producer for new instances of entity {@link OpenAssetMasterAction}.
 *
 * @author Developers
 *
 */
public class OpenAssetMasterActionProducer extends AbstractProducerForOpenEntityMasterAction<Asset, OpenAssetMasterAction> {

    @Inject
    public OpenAssetMasterActionProducer(final EntityFactory factory, final ICompanionObjectFinder companionFinder) {
        super(factory, Asset.class, OpenAssetMasterAction.class, companionFinder);
    }

    @Override
    @Authorise(OpenAssetMasterAction_CanOpen_Token.class)
    protected OpenAssetMasterAction provideDefaultValues(OpenAssetMasterAction openAction) {
        return super.provideDefaultValues(openAction);
    }
}
