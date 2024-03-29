package helsinki.example;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.test.ioc.UniversalConstantsForTesting;
import ua.com.fielden.platform.utils.IUniversalConstants;

import helsinki.personnel.Person;
import helsinki.personnel.validator.NoSpacesValidator;
import helsinki.test_config.AbstractDomainTestCase;
import static metamodels.MetaModels.Person_;

/**
 * This is an example unit test, which can be used as a starting point for creating application unit tests.
 *
 * @author Generated
 */
public class PersonnelTest extends AbstractDomainTestCase {

    /**
     * The names of the test method should be informative.
     * It is recommended to make the method name sound like a sentence stating the expected behaviour.
     * In this case, the test method name indicates that it is expected to find person with initials RDM and that it has an active status.
     * <p>
     * Each test method should be related to exactly one concern, which facilitates creation of unit tests that address a single concern.
     */
    @Test
    public void person_RMD_is_present_and_active() {
        final Person person = co(Person.class).findByKey("RMD@organisation.com");
        assertNotNull(person);
        assertTrue(person.isActive());
    }

    @Test
    public void person_JC_is_present_but_not_active() {
        final Person person = co(Person.class).findByKey("JC@organisation.com");
        assertNotNull(person);
        assertFalse(person.isActive());
    }
    
    @Test
    public void desc_represents_full_name() {
        final Person newPerson = new_composite(Person.class, "person@helsinki").setName("Name").setSurname("Surname");

        assertNull(newPerson.getDesc());

        final Person person = save(newPerson);
        assertEquals("Name Surname", person.getDesc());

    }
    
    @Test
    public void emploeeNo_requires_dob() {
        final Person person = new_composite(Person.class, "person@helsinki").setName("Name").setSurname("Surname");

        person.setEmployeeNo("Worker");
        person.setDob(null);

        assertNotNull(person);

        final MetaProperty<String> mpDob = person.getProperty(Person_.dob());
        assertFalse(mpDob.isValid());
        assertEquals("Required property [DOB] is not specified for entity [Person].", mpDob.getFirstFailure().getMessage());
    }
    
    @Test
    public void dob_is_not_future() throws ParseException {
        final Person person = new_composite(Person.class, "person@helsinki");
        
        String date_string = "30-05-2024";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");      
        Date newDate = formatter.parse(date_string);     
        
        person.setDob(newDate);
        
        assertNotNull(person);
        
        final MetaProperty<String> mpDob = person.getProperty(Person_.dob());
        assertFalse(mpDob.isValid());
        assertEquals("You can't enter the date of birth that is in future!", mpDob.getFirstFailure().getMessage());
    }
    
    @Test
    public void dob_is_not_old() throws ParseException {
        final Person person = new_composite(Person.class, "person@helsinki");
        
        String date_string = "30-05-1900";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");      
        Date newDate = formatter.parse(date_string);     
        
        person.setDob(newDate);
        
        assertNotNull(person);
        
        final MetaProperty<String> mpDob = person.getProperty(Person_.dob());
        assertFalse(mpDob.isValid());
        assertEquals("Too old date of birth!", mpDob.getFirstFailure().getMessage());
    }
    
    
    @Test
    public void name_does_not_permit_spaces() {
        final Person person = new_composite(Person.class, "person@helsinki");
        person.setName("Space value");
        
        assertNotNull(person);
        
        final MetaProperty<String> mpName = person.getProperty(Person_.name());
        assertFalse(mpName.isValid());
        assertEquals(NoSpacesValidator.ERR_SPACES.formatted(mpName.getTitle(), Person.ENTITY_TITLE), mpName.getFirstFailure().getMessage());
        assertEquals("Space value", mpName.getLastInvalidValue());
    }
    
    @Test
    public void surname_does_not_permit_spaces() {
        final Person person = new_composite(Person.class, "person@helsinki");
        person.setSurname("Space value");
        
        assertNotNull(person);
        
        final MetaProperty<String> mpSurname = person.getProperty(Person_.surname());
        assertFalse(mpSurname.isValid());
        assertEquals(NoSpacesValidator.ERR_SPACES.formatted(mpSurname.getTitle(), Person.ENTITY_TITLE), mpSurname.getFirstFailure().getMessage());
        assertEquals("Space value", mpSurname.getLastInvalidValue());
    }

    /**
     * In case of a complex data population it is possible to store the data into a script by changing this method to return <code>true</code>.
     * <p>
     * This way it is possible to reuse it later in place of re-running the data population logic, which is a lot faster.
     * Please also refer method {@link #useSavedDataPopulationScript()} below -- the values returned by this and that method cannot be <code>true</code> simultaneously.
     */
    @Override
    public boolean saveDataPopulationScriptToFile() {
        return false;
    }

    /**
     * If the test data was populated and saved as a script file (hinted in method {@link #saveDataPopulationScriptToFile()} above),
     * then this method can be changed to return <code>true</code> in order to avoid execution of the data population logic and simply execute the saved script.
     * This makes the population of the test data a lot faster.
     * It is very convenient when there is a need to run the same test case multiple times interactively.
     * <p>
     * However, this method should never return <code>true</code> when running multiple test cases.
     * Therefore, it is important to change this method to return <code>false</code> before committing changes into your VCS such as Git.
     */
    @Override
    public boolean useSavedDataPopulationScript() {
        return false;
    }

    /**
     * Domain state population method.
     * <p>
     * <b>IMPORTANT:</p> this method executes only once for a Test Case. At the same time, new instances of a Test Case are created for each test method.
     * Thus, this method should not be used for initialisation of the Test Case state other than the persisted domain state.
     */
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

        // Here the three Person entities are persisted using the the inherited from TG testing framework methods.
        save(new_(Person.class).setEmail("RMD@organisation.com").setName("Ronald").setSurname("McDonald").setActive(true));
        save(new_(Person.class).setEmail("JC@organisation.com").setName("John").setSurname("Carmack").setActive(false));
    }

}
