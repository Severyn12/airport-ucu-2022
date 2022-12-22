package helsinki.asset;

import com.google.inject.Inject;

import static helsinki.asset.Asset.ASSET_NO_LENGTH;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import helsinki.security.tokens.persistent.Asset_CanSave_Token;
import helsinki.security.tokens.persistent.Asset_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.keygen.IKeyNumber;
import ua.com.fielden.platform.keygen.KeyNumber;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link AssetCo}.
 *
 * @author Developers
 *
 */
@EntityType(Asset.class)
public class AssetDao extends CommonEntityDao<Asset> implements AssetCo {

    public static final String ASSET_NO = "ASSET";
    public static final String DEFAULT_ASSET_NO = "TBD";
    
    @Inject
    public AssetDao(final IFilter filter) {
        super(filter);
    } 
    
    @Override
        public Asset new_() {
            return super.new_().setNumber(DEFAULT_ASSET_NO);
        }

    @Override
    @SessionRequired
    @Authorise(Asset_CanSave_Token.class)
    public Asset save(Asset asset) {
        asset.isValid().ifFailure(Result::throwRuntime);
        
        final boolean wasPersisted = asset.isPersisted();
        try {
            if (!wasPersisted) {
                final IKeyNumber coKeyNumber = co(KeyNumber.class);
                final var nextAssetNo = StringUtils.leftPad(coKeyNumber.nextNumber(ASSET_NO).toString(), ASSET_NO_LENGTH, "0"); 
                asset.setNumber(nextAssetNo);
    
            }
        } catch (final Exception ex) {
            if (!wasPersisted) {
                asset.setNumber(DEFAULT_ASSET_NO);
            }
            throw ex;
        }
        
        return super.save(asset);
    }

    @Override
    @SessionRequired
    @Authorise(Asset_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(Asset_CanDelete_Token.class)
    public int batchDelete(final List<Asset> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<Asset> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}