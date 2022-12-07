package helsinki.personnel.validator;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class DobConstraintValidator extends AbstractBeforeChangeEventHandler<Date> {

    @Override
    public Result handle(final MetaProperty<Date> property, Date newValue, Set<Annotation> mutatorAnnotations) {
        
        
        SimpleDateFormat formatNowYear = new SimpleDateFormat("yyyy");
        Date currentDate = new Date();


        int currentYear = Integer.parseInt(formatNowYear.format(currentDate)); 
        int dobYear = Integer.parseInt(formatNowYear.format(newValue));

        if (newValue.after(currentDate)) {
            return Result.failure("You can't enter the date of birth that is in future!");
        }
        
        else if (currentYear - dobYear >= 100) {
            return Result.failure("Too old date of birth!");
        } 
        
        else {
            return Result.successful(newValue);
        }
    }

}
