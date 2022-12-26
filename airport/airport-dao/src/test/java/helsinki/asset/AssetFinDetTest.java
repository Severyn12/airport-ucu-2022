package helsinki.asset;

import static helsinki.asset.AssetFinDetCo.ERR_INITIAL_COST_IS_NOT_SPECIFIED_FOR_COMISSION_DATE;
import static metamodels.MetaModels.AssetFinDet_;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import helsinki.test_config.AbstractDomainTestCase;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.test.ioc.UniversalConstantsForTesting;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.utils.IUniversalConstants;


public class AssetFinDetTest extends AbstractDomainTestCase {
    

    
    @Test
    public void basic_date_range_integrity_constraints_work_for_comissionDate_and_desposalDate() {
                 
      final AssetFinDet generatorFinDet = co(AssetFinDet.class).findByKeyAndFetch(AssetFinDetCo.FETCH_PROVIDER.fetchModel(), createNewAssets(1).get(0));
      assertNotNull(generatorFinDet);
      assertNull(generatorFinDet.getInitCost());
      assertNull(generatorFinDet.getComissionDate());
      assertNull(generatorFinDet.getDisposalDate());
      
      final Date comissionDate = dateTime("2022-12-14 10:00:00").toDate();
      final Date desposalDate = dateTime("2023-12-14 10:00:00").toDate();

      generatorFinDet.setComissionDate(comissionDate);
      generatorFinDet.setDisposalDate(desposalDate);

      
      assertEquals(comissionDate, generatorFinDet.getComissionDate());
      assertEquals(desposalDate, generatorFinDet.getDisposalDate());

    }
    
    @Test
    public void comissionDate_cannot_be_after_desposalDate() {
        final AssetFinDet generatorFinDet = co$(AssetFinDet.class).findByKeyAndFetch(AssetFinDetCo.FETCH_PROVIDER.fetchModel(), createNewAssets(1).get(0));
        
        final Date comissionDate = dateTime("2022-12-14 10:00:00").toDate();
        generatorFinDet.setComissionDate(comissionDate);
        assertEquals(comissionDate, generatorFinDet.getComissionDate());
        
        final Date desposalDate = dateTime("2023-12-14 10:00:00").toDate();
        generatorFinDet.setDisposalDate(desposalDate);
        assertEquals(desposalDate, generatorFinDet.getDisposalDate());
        
        final Date newFutureComissionDate = dateTime("2024-12-14 10:00:00").toDate();
        generatorFinDet.setComissionDate(newFutureComissionDate);
        
        final MetaProperty<Date> mpComissionDate = generatorFinDet.getProperty(AssetFinDet_.comissionDate());
        assertFalse(mpComissionDate.isValid());
        assertEquals(comissionDate, generatorFinDet.getComissionDate());
        
        final Date newFutureDesposalDate = dateTime("2025-12-14 10:00:00").toDate();
        generatorFinDet.setDisposalDate(newFutureDesposalDate);
        assertTrue(mpComissionDate.isValid());
        assertEquals(newFutureComissionDate, generatorFinDet.getComissionDate());
        
    }
    @Test
    public void desposalDate_cannot_be_before_comissionDate() {
        final AssetFinDet generatorFinDet = co$(AssetFinDet.class).findByKeyAndFetch(AssetFinDetCo.FETCH_PROVIDER.fetchModel(), createNewAssets(1).get(0));
            
        final Date comissionDate = dateTime("2022-12-14 10:00:00").toDate();
        generatorFinDet.setComissionDate(comissionDate);
        assertEquals(comissionDate, generatorFinDet.getComissionDate());
            
        final Date desposalDate = dateTime("2023-12-14 10:00:00").toDate();
        generatorFinDet.setDisposalDate(desposalDate);
        assertEquals(desposalDate, generatorFinDet.getDisposalDate());
          
        final MetaProperty<Date> mpDesposalDate = generatorFinDet.getProperty(AssetFinDet_.disposalDate());
        final Date newFutureDesposalDate = dateTime("2021-12-14 10:00:00").toDate();
        generatorFinDet.setDisposalDate(newFutureDesposalDate);
            
        assertFalse(mpDesposalDate.isValid());
        assertEquals(desposalDate, generatorFinDet.getDisposalDate());
            
        final Date newFutureComissionDate = dateTime("2020-12-14 10:00:00").toDate();
        generatorFinDet.setComissionDate(newFutureComissionDate);
        assertTrue(mpDesposalDate.isValid());
        assertEquals(newFutureDesposalDate, generatorFinDet.getDisposalDate());          
  
    }
    
    @Test
    public void comissionDate_requires_initial_cost() {
        final AssetFinDet generatorFinDet = co$(AssetFinDet.class).findByKeyAndFetch(AssetFinDetCo.FETCH_PROVIDER.fetchModel(), createNewAssets(1).get(0));
        final Date comissionDate = dateTime("2022-12-14 10:00:00").toDate();
        
        generatorFinDet.setComissionDate(comissionDate);
        generatorFinDet.setInitCost(null);

        assertNotNull(generatorFinDet);

        final MetaProperty<Money> mpInitCost = generatorFinDet.getProperty(AssetFinDet_.initCost());
        assertFalse(mpInitCost.isValid());
        assertEquals(ERR_INITIAL_COST_IS_NOT_SPECIFIED_FOR_COMISSION_DATE, mpInitCost.getFirstFailure().getMessage());
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
