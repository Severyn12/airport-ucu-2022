package helsinki.webapp.config.asset;

import static ua.com.fielden.platform.web.PrefDim.mkDim;
import static helsinki.common.StandardScrollingConfigs.standardEmbeddedScrollingConfig;
import static helsinki.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static helsinki.common.StandardActions.actionAddDesc;
import static helsinki.common.StandardActions.actionEditDesc;
import static java.lang.String.format;
import static metamodels.MetaModels.AssetFinDet_;
import static metamodels.MetaModels.Asset_;
import static ua.com.fielden.platform.dao.AbstractOpenCompoundMasterDao.enhanceEmbededCentreQuery;
import static ua.com.fielden.platform.entity_centre.review.DynamicQueryBuilder.createConditionProperty;

import java.util.Optional;

import com.google.inject.Injector;

import helsinki.asset.Asset;
import helsinki.asset.AssetClass;
import helsinki.asset.AssetFinDet;
import helsinki.asset.AssetType;
import helsinki.asset.master.menu.actions.AssetMaster_OpenAssetFinDet_MenuItem;
import helsinki.asset.producers.AssetFinDetProducer;
import helsinki.asset.ui_actions.OpenAssetMasterAction;
import helsinki.asset.ui_actions.producers.OpenAssetMasterActionProducer;
import helsinki.asset.master.menu.actions.AssetMaster_OpenMain_MenuItem;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;
import ua.com.fielden.platform.web.view.master.api.compound.Compound;
import ua.com.fielden.platform.web.view.master.api.compound.impl.CompoundMasterBuilder;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.PrefDim;
import ua.com.fielden.platform.web.PrefDim.Unit;
import helsinki.common.LayoutComposer;
import helsinki.common.StandardActions;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import helsinki.main.menu.asset.MiAsset;
import metamodels.MetaModels;
import ua.com.fielden.platform.web.view.master.EntityMaster;
/**
 * {@link Asset} Web UI configuration.
 *
 * @author Developers
 *
 */
public class AssetWebUiConfig {

    private final Injector injector;

    public final EntityCentre<Asset> centre;
    public final EntityMaster<Asset> master;
    public final EntityMaster<OpenAssetMasterAction> compoundMaster;
    public final EntityActionConfig editAssetAction; // should be used on embedded centres instead of a standard EDIT action
    public final EntityActionConfig newAssetWithMasterAction; // should be used on embedded centres instead of a standrad NEW action
    private final EntityActionConfig newAssetAction;

    public static AssetWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new AssetWebUiConfig(injector, builder);
    }

    private AssetWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        this.injector = injector;

        final PrefDim dims = mkDim(960, 640, Unit.PX);
        editAssetAction = Compound.openEdit(OpenAssetMasterAction.class, Asset.ENTITY_TITLE, actionEditDesc(Asset.ENTITY_TITLE), dims);
        newAssetWithMasterAction = Compound.openNewWithMaster(OpenAssetMasterAction.class, "add-circle-outline", Asset.ENTITY_TITLE, actionAddDesc(Asset.ENTITY_TITLE), dims);
        newAssetAction = Compound.openNew(OpenAssetMasterAction.class, "add-circle-outline", Asset.ENTITY_TITLE, actionAddDesc(Asset.ENTITY_TITLE), dims);
        builder.registerOpenMasterAction(Asset.class, editAssetAction);

        centre = createAssetCentre(builder);
        builder.register(centre);

        master = createAssetMaster();
        builder.register(master);

        compoundMaster = CompoundMasterBuilder.<Asset, OpenAssetMasterAction>create(injector, builder)
            .forEntity(OpenAssetMasterAction.class)
            .withProducer(OpenAssetMasterActionProducer.class)
            .addMenuItem(AssetMaster_OpenMain_MenuItem.class)
                .icon("icons:picture-in-picture")
                .shortDesc(OpenAssetMasterAction.MAIN)
                .longDesc(Asset.ENTITY_TITLE + " main")
                .withView(master)
            .also()
            .addMenuItem(AssetMaster_OpenAssetFinDet_MenuItem.class)
                .icon("icons:view-module")
                .shortDesc(OpenAssetMasterAction.ASSETFINDETS)
                .longDesc(Asset.ENTITY_TITLE + " " + OpenAssetMasterAction.ASSETFINDETS)
                .withView(createAssetFinDetMaster())
            .done();
        builder.register(compoundMaster);
    }

    /**
     * Creates entity centre for {@link Asset}.
     *
     * @return
     */
    private EntityCentre<Asset> createAssetCentre(final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkVarGridForCentre(2, 2, 1);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Asset.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Asset.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityCentreConfig<Asset> ecc = EntityCentreBuilder.centreFor(Asset.class)
                .addFrontAction(newAssetAction)
                .addTopAction(newAssetAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit(Asset_).asMulti().autocompleter(Asset.class).also()
                .addCrit(Asset_.active()).asMulti().bool().also()
                .addCrit(Asset_.assetType()).asMulti().autocompleter(AssetType.class).also()
                .addCrit(Asset_.assetType().assetClass()).asMulti().autocompleter(AssetClass.class).also()

                .addCrit(Asset_.desc()).asMulti().text()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp(Asset_).order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Asset.ENTITY_TITLE)).also()
                .addProp(Asset_.desc()).minWidth(200).also()
                .addProp(Asset_.assetType()).minWidth(200).also()
                .addProp(Asset_.assetType().assetClass()).minWidth(200).also()
                .addProp(Asset_.active()).minWidth(100)



                .addPrimaryAction(editAssetAction)
                .build();

        return new EntityCentre<>(MiAsset.class, ecc, injector);
    }

    /**
     * Creates entity master for {@link Asset}.
     *
     * @return
     */
    private EntityMaster<Asset> createAssetMaster() {
        final String layout = LayoutComposer.mkVarGridForMasterFitWidth(2, 1, 1);

        final IMaster<Asset> masterConfig = new SimpleMasterBuilder<Asset>().forEntity(Asset.class)
                .addProp(Asset_.number()).asSinglelineText().also()
                .addProp(Asset_.active()).asCheckbox().also()
                .addProp(Asset_.desc()).asMultilineText().also()
                .addProp(Asset_.assetType()).asAutocompleter().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .done();

        return new EntityMaster<Asset>(Asset.class, masterConfig, injector);
    }

    private EntityMaster<AssetFinDet> createAssetFinDetMaster() {

        final String layout = LayoutComposer.mkVarGridForMasterFitWidth(1, 2);

        final IMaster<AssetFinDet> config = new SimpleMasterBuilder<AssetFinDet>().forEntity(AssetFinDet.class)
                // row 1
                .addProp(AssetFinDet_.initCost()).asMoney().also()
                .addProp(AssetFinDet_.comissionDate()).asDateTimePicker().also()
                .addProp(AssetFinDet_.disposalDate()).asDateTimePicker().also()

                // entity actions
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancels current changes if any or refresh the data")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .done();

        return new EntityMaster<AssetFinDet>(
                AssetFinDet.class,
                AssetFinDetProducer.class,
                config,
                injector);
    }
}