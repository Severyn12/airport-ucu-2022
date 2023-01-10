package helsinki.asset;

import static helsinki.asset.AssetOwnershipCo.ERR_PERSISTED_ASSET_OWNERSHIP_CANNOT_BE_CHANGED;
import static metamodels.MetaModels.AssetOwnership_;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import helsinki.test_config.AbstractDomainTestCase;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.test.ioc.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;


public class AssetOwnershipTest extends AbstractDomainTestCase {
    
    
    @Test
    public void new_asset_ownership_has_all_ownership_properties_required() {
        final var asset = createNewAssets(1).get(0);
        final var ao = co(AssetOwnership.class).new_();
        
        ao.setAsset(asset);
        ao.setStartDate(date("2022-12-15 10:00:00"));
        
        assertTrue(ao.getProperty(AssetOwnership_.role()).isRequired());
        assertTrue(ao.getProperty(AssetOwnership_.businessUnit()).isRequired());
        assertTrue(ao.getProperty(AssetOwnership_.organisation()).isRequired());
        
        final var validationResult = ao.isValid();
        
        assertFalse(validationResult.isSuccessful());
        assertEquals(AssetOwnershipCo.ER_ONE_OF_OWNERSHIP_PROPS_IS_REQUIRED, validationResult.getMessage());
        
    }
    
    @Test
    public void for_new_asset_ownership_if_one_of_the_ownership_props_has_value_it_is_required_and_two_other_have_no_value_and_are_not_required() {
        final var asset = createNewAssets(1).get(0);
        final var ao = co(AssetOwnership.class).new_();
        
        ao.setAsset(asset);
        ao.setStartDate(date("2022-12-15 10:00:00"));
        
        final var mpRole = ao.getProperty(AssetOwnership_.role());
        final var mpBusinessUnit = ao.getProperty(AssetOwnership_.businessUnit());
        final var mpOrganisation = ao.getProperty(AssetOwnership_.organisation());
        
        assertTrue(mpRole.isRequired());
        assertNull(ao.getRole());
        
        assertTrue(mpBusinessUnit.isRequired());
        assertNull(ao.getBusinessUnit());
        
        assertTrue(mpOrganisation.isRequired());
        assertNull(ao.getOrganisation());

        ao.setRole("Role 1");
        assertTrue(mpRole.isRequired());
        assertEquals("Role 1", ao.getRole());
        
        assertFalse(mpBusinessUnit.isRequired());
        assertNull(ao.getBusinessUnit());
        assertFalse(mpOrganisation.isRequired());
        assertNull(ao.getOrganisation());

        ao.setBusinessUnit("Business unit 1");
        assertTrue(mpBusinessUnit.isRequired());
        assertEquals("Business unit 1", ao.getBusinessUnit());
        
        assertFalse(mpRole.isRequired());
        assertNull(ao.getRole());
        assertFalse(mpOrganisation.isRequired());
        assertNull(ao.getOrganisation());
        
        ao.setOrganisation("Organisation 1");
        assertTrue(mpOrganisation.isRequired());
        assertEquals("Organisation 1", ao.getOrganisation());
        
        assertFalse(mpRole.isRequired());
        assertNull(ao.getRole());
        assertFalse(mpBusinessUnit.isRequired());
        assertNull(ao.getBusinessUnit());
    }
    
    @Test
    public void for_just_retreiving_asset_ownership_only_the_ownership_prop_that_has_value_is_required() {
        final var asset = createNewAssets(1).get(0);
        final var ao = save(co(AssetOwnership.class).new_().setAsset(asset).setStartDate(date("2022-12-15 10:00:00")).setRole("Role 1"));
        
        assertTrue(ao.isPersisted());
        
        final var mpRole = ao.getProperty(AssetOwnership_.role());
        final var mpBusinessUnit = ao.getProperty(AssetOwnership_.businessUnit());
        final var mpOrganisation = ao.getProperty(AssetOwnership_.organisation());
        
        assertTrue(mpRole.isRequired());
        assertEquals("Role 1", ao.getRole());
        
        assertFalse(mpBusinessUnit.isRequired());
        assertNull(ao.getBusinessUnit());
        assertFalse(mpOrganisation.isRequired());
        assertNull(ao.getOrganisation());
    
    }
    
    @Test
    public void persisted_asset_ownership_cannot_be_changed() {
        final var asset = createNewAssets(1).get(0);
        final var ao = save(co(AssetOwnership.class).new_().setAsset(asset).setStartDate(date("2022-12-15 10:00:00")).setRole("Role 1"));
        
        assertTrue(ao.isPersisted());
        
        try {
            ao.setBusinessUnit("BU 1");
            fail("No mutation should have been permitted.");
            
        }catch (final Result ex){
            assertEquals(ERR_PERSISTED_ASSET_OWNERSHIP_CANNOT_BE_CHANGED, ex.getMessage());
        }
        
        try {
            ao.setStartDate(date("2022-12-16 10:00:00"));
            fail("No mutation should have been permitted.");
            
        }catch (final Result ex){
            assertEquals(ERR_PERSISTED_ASSET_OWNERSHIP_CANNOT_BE_CHANGED, ex.getMessage());
        }
        
        try {
            ao.setOrganisation("Organisation 1");
            fail("No mutation should have been permitted.");
            
        }catch (final Result ex){
            assertEquals(ERR_PERSISTED_ASSET_OWNERSHIP_CANNOT_BE_CHANGED, ex.getMessage());
        }

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
    
    private List<Asset> createNewAssets(final int numOfAssetsToBeCreated){
        return AssetTest.createNewAssets(numOfAssetsToBeCreated, this);
    }

}
