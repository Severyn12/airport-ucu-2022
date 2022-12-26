package helsinki.asset;

import static metamodels.MetaModels.Asset_;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.cond;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.expr;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAggregates;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAll;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAllAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAllInclCalc;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAllInclCalcAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchIdOnly;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchKeyAndDescOnly;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchKeyAndDescOnlyAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchOnly;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchOnlyAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.from;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.orderBy;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;
import static ua.com.fielden.platform.utils.EntityUtils.fetch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import helsinki.test_config.AbstractDomainTestCase;
import metamodels.MetaModels;
import ua.com.fielden.platform.dao.QueryExecutionModel;
import ua.com.fielden.platform.dao.exceptions.EntityAlreadyExists;
import ua.com.fielden.platform.entity.query.EntityAggregates;
import ua.com.fielden.platform.entity.query.fluent.fetch;
import ua.com.fielden.platform.entity.query.model.AggregatedResultQueryModel;
import ua.com.fielden.platform.entity.query.model.EntityResultQueryModel;
import ua.com.fielden.platform.entity.query.model.OrderingModel;
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
    public void newly_created_asset_has_sequential_number_key() {
        
        final Asset generator  = createNewAssets(1).get(0);
        assertEquals(StringUtils.leftPad("1", Asset.ASSET_NO_LENGTH, "0"), generator.getNumber());
      
    }
    
    @Test
    public void saving_modified_persisted_asset_does_not_change_its_number() {
            
        final Asset generator  = createNewAssets(1).get(0);
        final Asset updGenerator = save(generator.setDesc("Upd description"));
        
        assertEquals(generator.getVersion() + 0, updGenerator.getVersion() - 1);
        assertEquals(generator.getNumber(), updGenerator.getNumber());
      
    }  
    
    @Test
    public void newly_created_asset_has_fin_det_instances_also_saved() {
        
        final Asset generator  = createNewAssets(1).get(0);
         
        final AssetFinDet generatorFinDet = co(AssetFinDet.class).findByKeyAndFetch(AssetFinDetCo.FETCH_PROVIDER.fetchModel(), generator);
        assertNotNull(generatorFinDet);
        assertNull(generatorFinDet.getInitCost());
        assertNull(generatorFinDet.getComissionDate());
        assertNull(generatorFinDet.getDisposalDate());
      
    }
    
    @Test
    public void saving_modified_persistent_asset_does_not_attempt_to_create_a_new_fin_det_instance() {
              
        final Asset generator  = createNewAssets(1).get(0);
        try {
        save(generator.setDesc("Upd description"));
        } catch (final EntityAlreadyExists ex) {
            
            fail("Exception %s is not expected: %s".formatted(ex.getClass().getSimpleName(), ex.getMessage()));
        }

    }
    
    @Test
    public void assets_can_be_deleted_by_their_Ids() {

        final List<Long> assetIds = createNewAssets(3).stream().map(asset -> asset.getId()).toList();
        co(Asset.class).batchDelete(assetIds);
        
       assertFalse(co(Asset.class).exists(select(Asset.class).where().prop(Asset_.id()).in().values(assetIds.toArray()).model()));
       assertFalse(co(AssetFinDet.class).exists(select(AssetFinDet.class).where().prop(Asset_.id()).in().values(assetIds.toArray()).model()));

        
    }
    
    @Test
    public void assets_can_be_by_their_references() {

        final List<Asset> generators = createNewAssets(3);
        co(Asset.class).batchDelete(generators);
        
        assertFalse(co(AssetFinDet.class).exists(select(AssetFinDet.class).where().prop(Asset_.id()).in().values(generators.stream().map(asset -> asset.getId()).toArray()).model()));

        
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
        return createNewAssets(numOfAssetsToBeCreated, this);
    }

    
    public static List<Asset> createNewAssets(final int numOfAssetsToBeCreated, final AbstractDomainTestCase testCase){
        final var assets = new ArrayList<Asset>();
        
        final AssetCo coAsset = testCase.co(Asset.class);
        final AssetType generators = testCase.co(AssetType.class).findByKeyAndFetch(AssetCo.FETCH_PROVIDER.<AssetType>fetchFor(MetaModels.Asset_.assetType()).fetchModel(), "Generators");
        assertNotNull(generators);
        
        for (int count =0; count <numOfAssetsToBeCreated; count++) {
            assets.add(testCase.save(coAsset.new_().setAssetType(generators).setDesc("Description")));
        }
        return assets;
        
    }

}
