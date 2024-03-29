package helsinki.dev_mod.util;

import static java.lang.String.format;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.PostgreSQL82Dialect;

import static org.apache.logging.log4j.LogManager.getLogger;
import org.apache.logging.log4j.Logger;

import helsinki.asset.Asset;
import helsinki.asset.AssetClass;
import helsinki.asset.AssetCo;
import helsinki.asset.AssetOwnership;
import helsinki.asset.AssetType;
import helsinki.config.ApplicationDomain;
import helsinki.data.IDomainData;
import helsinki.utils.PostgresqlDbUtils;

import ua.com.fielden.platform.devdb_support.DomainDrivenDataPopulation;
import ua.com.fielden.platform.entity.AbstractEntity;
import ua.com.fielden.platform.persistence.HibernateUtil;
import ua.com.fielden.platform.security.user.User;
import ua.com.fielden.platform.test.IDomainDrivenTestCaseConfiguration;
import ua.com.fielden.platform.utils.DbUtils;

/**
 * This is a convenience class for (re-)creation of the development database and its population.
 *
 * It contains the <code>main</code> method and can be executed whenever the target database needs to be (re-)set.
 * <p>
 *
 * <b>IMPORTANT: </b><i>One should be careful not to run this code against the deployment or production databases, which would lead to the loss of all data.</i>
 *
 * <p>
 *
 * @author Generated
 */
public class PopulateDb extends DomainDrivenDataPopulation implements IDomainData {

    private static final Logger LOGGER = getLogger(PopulateDb.class);

    private final ApplicationDomain applicationDomainProvider = new ApplicationDomain();

    private PopulateDb(final IDomainDrivenTestCaseConfiguration config, final Properties props) {
        super(config, props);
    }

    public static void main(final String[] args) throws Exception {
        LOGGER.info("Initialising...");
        final String configFileName = args.length == 1 ? args[0] : "application.properties";
        final Properties props = new Properties();
        try (final FileInputStream in = new FileInputStream(configFileName)) {
            props.load(in);
        }

        LOGGER.info("Obtaining Hibernate dialect...");
        final Class<?> dialectType = Class.forName(props.getProperty("hibernate.dialect"));
        final Dialect dialect = (Dialect) dialectType.getDeclaredConstructor().newInstance();
        LOGGER.info(format("Running with dialect %s...", dialect));
        final DataPopulationConfig config = new DataPopulationConfig(props);
        LOGGER.info("Generating DDL and running it against the target DB...");

        // use TG DDL generation or
        // Hibernate DDL generation final List<String> createDdl = DbUtils.generateSchemaByHibernate()
        final List<String> createDdl = config.getDomainMetadata().generateDatabaseDdl(dialect);
        final List<String> ddl = dialect instanceof H2Dialect ? DbUtils.prependDropDdlForH2(createDdl) :
                                 dialect instanceof PostgreSQL82Dialect ? PostgresqlDbUtils.prependDropDdlForPostgresql(createDdl) :
                                 DbUtils.prependDropDdlForSqlServer(createDdl);

        DbUtils.execSql(ddl, config.getInstance(HibernateUtil.class).getSessionFactory().getCurrentSession());

        final PopulateDb popDb = new PopulateDb(config, props);
        popDb.populateDomain();
    }

    @Override
    protected void populateDomain() {
        LOGGER.info("Creating and populating the development database...");

        setupUser(User.system_users.SU, "helsinki");
        setupPerson(User.system_users.SU, "helsinki", "Super", "User");
        
        final AssetClass acElectrical = save(new_composite(AssetClass.class, "Electrical").setDesc("Electrical equipment"));
        final AssetClass acVehicle = save(new_composite(AssetClass.class, "Vehicles").setDesc("Vehicle-like equipment"));
         
        final AssetType generators = save(new_composite(AssetType.class, "Generators").setAssetClass(acElectrical).setDesc("Electrical generation equipment."));
        save(new_composite(AssetType.class, "Fire engines").setAssetClass(acVehicle).setDesc("Fire engines equipment"));
        save(new_composite(AssetType.class, "Hovercraft").setAssetClass(acVehicle).setDesc("Hovercraft equipment"));

        final AssetCo coAsset = co(Asset.class);
        final var generator = save(coAsset.new_().setAssetType(generators).setDesc("Some description"));
        save(new_composite(AssetOwnership.class, generator, date("2020-10-11 00:00:00")).setRole("Role 1"));
        save(new_composite(AssetOwnership.class, generator, date("2021-10-11 00:00:00")).setBusinessUnit("Business unit 1"));
        save(new_composite(AssetOwnership.class, generator, date("2022-10-11 00:00:00")).setBusinessUnit("Business unit 2"));

        
        LOGGER.info("Completed database creation and population.");
        
    }

    @Override
    protected List<Class<? extends AbstractEntity<?>>> domainEntityTypes() {
        return applicationDomainProvider.entityTypes();
    }

}
