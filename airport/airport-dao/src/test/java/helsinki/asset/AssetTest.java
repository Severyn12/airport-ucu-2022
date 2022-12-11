package helsinki.asset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import helsinki.test_config.AbstractDomainTestCase;
import metamodels.MetaModels;
import ua.com.fielden.platform.keygen.IKeyNumber;
import ua.com.fielden.platform.keygen.KeyNumber;
import ua.com.fielden.platform.test.ioc.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;


public class AssetTest extends AbstractDomainTestCase {

    
    @Test
    public void new_not_yet_persisted_asset_have_a_default_number() {
        assertEquals(AssetDao.DEFAULT_ASSET_NO, new_(Asset.class).getNumber());
  

    }
    
    @Test
    public void newly_created_asset_has_sequentila_number() {
        
        final AssetCo coAsset = co(Asset.class);
        
        final AssetType generators = co(AssetType.class).findByKeyAndFetch(AssetCo.FETCH_PROVIDER.<AssetType>fetchFor(MetaModels.Asset_.assetType()).fetchModel(), "Generators");
        assertNotNull(generators);
        
        final Asset generator  = save(coAsset.new_().setAssetType(generators).setDesc("Description"));
        final IKeyNumber coKeyNumber = co(KeyNumber.class);
        assertEquals(StringUtils.leftPad("1", Asset.ASSET_NO_LENGTH, "0"), generator.getNumber());
      
    }
    
    @Test
    public void saving_modified_persistent_asset_does_not_change_its_number() {
        
        final AssetCo coAsset = co(Asset.class);
        
        final AssetType generators = co(AssetType.class).findByKeyAndFetch(AssetCo.FETCH_PROVIDER.<AssetType>fetchFor(MetaModels.Asset_.assetType()).fetchModel(), "Generators");        
        final Asset generator  = save(coAsset.new_().setAssetType(generators).setDesc("Description"));
        final Asset updGenerator = save(generator.setDesc("Upd description"));
        
        final IKeyNumber coKeyNumber = co(KeyNumber.class);
        
        assertEquals(generator.getVersion() + 0, updGenerator.getVersion() - 1);
        assertEquals(generator.getNumber(), updGenerator.getNumber());
      
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
