package helsinki.asset;

import com.google.inject.Inject;

import static metamodels.MetaModels.AssetOwnership_;

import java.util.Collection;
import java.util.List;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.persistent.AssetOwnership_CanSave_Token;
import metamodels.MetaModels;
import helsinki.security.tokens.persistent.AssetOwnership_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link AssetOwnershipCo}.
 *
 * @author Developers
 *
 */
@EntityType(AssetOwnership.class)
public class AssetOwnershipDao extends CommonEntityDao<AssetOwnership> implements AssetOwnershipCo {

    @Inject
    public AssetOwnershipDao(final IFilter filter) {
        super(filter);
    }
    
    @Override
    public AssetOwnership new_() {
        
        final var ao = super.new_();
        
        ao.getProperty(AssetOwnership_.role()).setRequired(true, ER_ONE_OF_OWNERSHIP_PROPS_IS_REQUIRED);
        ao.getProperty(AssetOwnership_.businessUnit()).setRequired(true, ER_ONE_OF_OWNERSHIP_PROPS_IS_REQUIRED);
        ao.getProperty(AssetOwnership_.organisation()).setRequired(true, ER_ONE_OF_OWNERSHIP_PROPS_IS_REQUIRED);
    
        return ao;
    }
    
    @Override
    @SessionRequired
    @Authorise(AssetOwnership_CanSave_Token.class)
    public AssetOwnership save(AssetOwnership entity) {
        return super.save(entity);
    }

    @Override
    @SessionRequired
    @Authorise(AssetOwnership_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(AssetOwnership_CanDelete_Token.class)
    public int batchDelete(final List<AssetOwnership> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<AssetOwnership> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}