package helsinki.asset;

import com.google.inject.Inject;

import java.util.Collection;
import java.util.List;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.persistent.AssetType_CanSave_Token;
import helsinki.security.tokens.persistent.AssetType_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link AssetTypeCo}.
 *
 * @author Developers
 *
 */
@EntityType(AssetType.class)
public class AssetTypeDao extends CommonEntityDao<AssetType> implements AssetTypeCo {

    @Inject
    public AssetTypeDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(AssetType_CanSave_Token.class)
    public AssetType save(AssetType entity) {
        return super.save(entity);
    }

    @Override
    @SessionRequired
    @Authorise(AssetType_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(AssetType_CanDelete_Token.class)
    public int batchDelete(final List<AssetType> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<AssetType> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}