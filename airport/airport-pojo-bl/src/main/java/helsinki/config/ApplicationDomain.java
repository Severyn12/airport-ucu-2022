package helsinki.config;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import helsinki.personnel.Person;
import ua.com.fielden.platform.basic.config.IApplicationDomainProvider;
import ua.com.fielden.platform.domain.PlatformDomainTypes;
import ua.com.fielden.platform.entity.AbstractEntity;
import helsinki.asset.AssetType;
import helsinki.asset.AssetClass;
import helsinki.asset.actions.AssetTypeUpdateAction;
import helsinki.asset.Asset;
import helsinki.asset.AssetFinDet;
import helsinki.asset.ui_actions.OpenAssetMasterAction;
import helsinki.asset.master.menu.actions.AssetMaster_OpenMain_MenuItem;
import helsinki.asset.master.menu.actions.AssetMaster_OpenAssetFinDet_MenuItem;
import helsinki.asset.AssetOwnership;
import helsinki.asset.master.menu.actions.AssetMaster_OpenAssetOwnership_MenuItem;

/**
 * A class to register domain entities.
 *
 * @author Generated
 */
public class ApplicationDomain implements IApplicationDomainProvider {

    private static final Set<Class<? extends AbstractEntity<?>>> entityTypes = new LinkedHashSet<>();
    private static final Set<Class<? extends AbstractEntity<?>>> domainTypes = new LinkedHashSet<>();

    static {
        entityTypes.addAll(PlatformDomainTypes.types);
        add(Person.class);
        add(AssetType.class);
        add(AssetClass.class);
        add(AssetTypeUpdateAction.class);
        add(Asset.class);
        add(AssetFinDet.class);
        add(OpenAssetMasterAction.class);
        add(AssetMaster_OpenMain_MenuItem.class);
        add(AssetMaster_OpenAssetFinDet_MenuItem.class);
        add(AssetOwnership.class);
        add(AssetMaster_OpenAssetOwnership_MenuItem.class);
    }

    private static void add(final Class<? extends AbstractEntity<?>> domainType) {
        entityTypes.add(domainType);
        domainTypes.add(domainType);
    }

    @Override
    public List<Class<? extends AbstractEntity<?>>> entityTypes() {
        return Collections.unmodifiableList(entityTypes.stream().collect(Collectors.toList()));
    }

    public List<Class<? extends AbstractEntity<?>>> domainTypes() {
        return Collections.unmodifiableList(domainTypes.stream().collect(Collectors.toList()));
    }

}
