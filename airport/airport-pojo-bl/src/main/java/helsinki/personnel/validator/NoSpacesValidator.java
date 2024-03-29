package helsinki.personnel.validator;

import java.lang.annotation.Annotation;
import java.util.Set;

import helsinki.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class NoSpacesValidator extends AbstractBeforeChangeEventHandler<String> {
    
    public static final String ERR_SPACES = "Spaces are not permitted for [%s] in entity [%s].";

    @Override
    public Result handle(final MetaProperty<String> property, String newValue, Set<Annotation> mutatorAnnotations) {
        if (newValue.contains(" ")) {
            return Result.failure(ERR_SPACES.formatted(property.getTitle(), Person.ENTITY_TITLE));
        } else {
            return Result.successful(newValue);
        }
    }

}
