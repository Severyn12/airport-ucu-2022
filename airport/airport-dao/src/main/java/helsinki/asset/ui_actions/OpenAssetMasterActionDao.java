package helsinki.asset.ui_actions;

import static metamodels.MetaModels.AssetOwnership_;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.AbstractOpenCompoundMasterDao;
import ua.com.fielden.platform.dao.IEntityAggregatesOperations;
import helsinki.asset.AssetOwnership;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link OpenAssetMasterActionCo}.
 *
 * @author Developers
 *
 */
@EntityType(OpenAssetMasterAction.class)
public class OpenAssetMasterActionDao extends AbstractOpenCompoundMasterDao<OpenAssetMasterAction> implements OpenAssetMasterActionCo {

    @Inject
    public OpenAssetMasterActionDao(final IFilter filter, final IEntityAggregatesOperations coAggregates) {
        super(filter, coAggregates);
        this.addViewBinding(OpenAssetMasterAction.ASSETOWNERSHIPS, AssetOwnership.class, AssetOwnership_.asset());
    }

    @Override
    protected IFetchProvider<OpenAssetMasterAction> createFetchProvider() {
        return FETCH_PROVIDER;
    }

}