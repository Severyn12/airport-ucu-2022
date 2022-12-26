package helsinki.asset;

import com.google.inject.Inject;

import java.util.Collection;
import java.util.List;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.persistent.AssetFinDet_CanSave_Token;
import helsinki.security.tokens.persistent.AssetFinDet_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link AssetFinDetCo}.
 *
 * @author Developers
 *
 */
@EntityType(AssetFinDet.class)
public class AssetFinDetDao extends CommonEntityDao<AssetFinDet> implements AssetFinDetCo {

    @Inject
    public AssetFinDetDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(AssetFinDet_CanSave_Token.class)
    public AssetFinDet save(AssetFinDet entity) {
        return super.save(entity);
    }

    @Override
    @SessionRequired
    @Authorise(AssetFinDet_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(AssetFinDet_CanDelete_Token.class)
    public int batchDelete(final List<AssetFinDet> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<AssetFinDet> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}