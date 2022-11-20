package helsinki.personnel.definers;

import org.apache.commons.lang.StringUtils;
import static metamodels.MetaModels.Person_;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractAfterChangeEventHandler;

public class MakeDobRequiredDefiner extends AbstractAfterChangeEventHandler<String> {

    @Override
    public void handle(final MetaProperty<String> mpEmplyeeNo, final String value) {
        mpEmplyeeNo.getEntity().getProperty(Person_.dob()).setRequired(!StringUtils.isEmpty(value));
    }

}
