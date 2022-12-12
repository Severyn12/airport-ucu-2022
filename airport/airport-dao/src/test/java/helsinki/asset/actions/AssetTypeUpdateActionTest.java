package helsinki.asset.actions;

import static metamodels.MetaModels.AssetTypeUpdateAction_;
import static metamodels.MetaModels.AssetType_;
import static metamodels.MetaModels.Person_;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.from;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.orderBy;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import helsinki.asset.AssetClass;
import helsinki.asset.AssetType;
import helsinki.asset.AssetTypeCo;
import helsinki.personnel.Person;
import helsinki.test_config.AbstractDomainTestCase;
import metamodels.MetaModels;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.test.ioc.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;


public class AssetTypeUpdateActionTest extends AbstractDomainTestCase {

    @Test
    public void asset_types_with_specified_ids__are_updated() {
        final AssetTypeCo co = co(AssetType.class);
        
        final var partialQ = select(AssetType.class).where().prop(AssetType_.name()).in().values("Generators", "Fire engines");   
        final var query = partialQ.model();
        final Set<Long> assetTypeIds = co.getAllEntities(from(query).with(orderBy().prop(AssetType_.name()).asc().model()).model()).stream().map(AssetType::getId).collect(Collectors.toSet());
        
        final AssetClass acHybrid = co(AssetClass.class).findByKeyAndFetch(AssetTypeUpdateActionCo.FETCH_PROVIDER.<AssetClass>fetchFor(AssetTypeUpdateAction_.assetClass()).fetchModel(), "Hybrid");
        assertNotNull(acHybrid);
        final var action = co(AssetTypeUpdateAction.class).new_().setSelectedAssetTypeIds(assetTypeIds).setAssetClass(acHybrid);
        co(AssetTypeUpdateAction.class).save(action);
        
        assertEquals(2, co.count(partialQ.and().prop(AssetType_.assetClass()).eq().val(acHybrid).model()));
        assertEquals(1, co.count(select(AssetType.class).where().prop(AssetType_.assetClass()).ne().val(acHybrid).model()));

    }
    
    @Test
    public void no_asset_types_get_updated_when_no_ids_specified() {
//        final AssetTypeCo co = co(AssetType.class);
//        
//        final var partialQ = select(AssetType.class).where().prop(AssetType_.name()).in().values("Generators", "Fire engines");   
//        final var query = partialQ.model();
//        final Set<Long> assetTypeIds = co.getAllEntities(from(query).with(orderBy().prop(AssetType_.name()).asc().model()).model()).stream().map(AssetType::getId).collect(Collectors.toSet());
        
        final AssetClass acHybrid = co(AssetClass.class).findByKeyAndFetch(AssetTypeUpdateActionCo.FETCH_PROVIDER.<AssetClass>fetchFor(AssetTypeUpdateAction_.assetClass()).fetchModel(), "Hybrid");
        assertNotNull(acHybrid);
        co(AssetTypeUpdateAction.class).save(co(AssetTypeUpdateAction.class).new_().setAssetClass(acHybrid));
        
        assertFalse(co(AssetType.class).exists(select(AssetType.class).where().prop(AssetType_.assetClass()).eq().val(acHybrid).model()));
//        assertFalse(2, co.count(partialQ.and().prop(AssetType_.assetClass()).eq().val(acHybrid).model()));
//        assertEquals(1, co.count(select(AssetType.class).where().prop(AssetType_.assetClass()).ne().val(acHybrid).model()));

    }

    @Override
    public boolean saveDataPopulationScriptToFile() {
        return false;
    }

    @Override
    public boolean useSavedDataPopulationScript() {
        return false;
    }

    @Override
    protected void populateDomain() {
        // Need to invoke super to create a test user that is responsible for data population
        super.populateDomain();

        // Here is how the Test Case universal constants can be set.
        // In this case the notion of now is overridden, which makes it possible to have an invariant system-time.
        // However, the now value should be after AbstractDomainTestCase.prePopulateNow in order not to introduce any date-related conflicts.
        final UniversalConstantsForTesting constants = (UniversalConstantsForTesting) getInstance(IUniversalConstants.class);
        constants.setNow(dateTime("2019-10-01 11:30:00"));

        // If the use of saved data population script is indicated then there is no need to proceed with any further data population logic.
        if (useSavedDataPopulationScript()) {
            return;
        }
        
        save(new_composite(AssetClass.class, "Hybrid").setDesc("Moving equipment"));
        final AssetClass acElectrical = save(new_composite(AssetClass.class, "Electrical").setDesc("Electrical equipment"));
        final AssetClass acVehicle = save(new_composite(AssetClass.class, "Vehicles").setDesc("Vehicle-like equipment"));
        
        save(new_composite(AssetType.class, "Generators").setAssetClass(acElectrical).setDesc("Electrical generation equipment"));
        save(new_composite(AssetType.class, "Fire engines").setAssetClass(acVehicle).setDesc("Fire engines equipment"));
        save(new_composite(AssetType.class, "Hovercraft").setAssetClass(acVehicle).setDesc("Hovercraft equipment"));


    }

}
