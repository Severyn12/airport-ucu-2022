package helsinki.asset;

import static metamodels.MetaModels.AssetClass_;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link AssetClass}.
 *
 * @author Developers
 *
 */
public interface AssetClassCo extends IEntityDao<AssetClass> {

    static final IFetchProvider<AssetClass> FETCH_PROVIDER = EntityUtils.fetch(AssetClass.class)
            .with(AssetClass_.name(), AssetClass_.desc(), AssetClass_.active());
}
