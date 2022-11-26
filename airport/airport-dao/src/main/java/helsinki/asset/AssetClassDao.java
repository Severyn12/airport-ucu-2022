package helsinki.asset;

import com.google.inject.Inject;

import java.util.Collection;
import java.util.List;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.persistent.AssetClass_CanSave_Token;
import helsinki.security.tokens.persistent.AssetClass_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link AssetClassCo}.
 *
 * @author Developers
 *
 */
@EntityType(AssetClass.class)
public class AssetClassDao extends CommonEntityDao<AssetClass> implements AssetClassCo {

    @Inject
    public AssetClassDao(final IFilter filter) {
        super(filter);
    }

    @Override
        public AssetClass new_() {
            // TODO Auto-generated method stub
            return super.new_().setActive(true);
        }
    
    @Override
    @SessionRequired
    @Authorise(AssetClass_CanSave_Token.class)
    public AssetClass save(AssetClass entity) {
        return super.save(entity);
    }

    @Override
    @SessionRequired
    @Authorise(AssetClass_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(AssetClass_CanDelete_Token.class)
    public int batchDelete(final List<AssetClass> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<AssetClass> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}