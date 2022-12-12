package helsinki.security.tokens.open_simple_master;

import static java.lang.String.format;

import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.tokens.Template;
import helsinki.asset.AssetClass;
import helsinki.security.tokens.UsersAndPersonnelModuleToken;

/**
 * A security token for entity {@link AssetClass} to guard Open.
 *
 * @author Developers
 *
 */
public class AssetClassMaster_CanOpen_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.MASTER_OPEN.forTitle(), AssetClass.ENTITY_TITLE + " Master");
    public final static String DESC = format(Template.MASTER_OPEN.forDesc(), AssetClass.ENTITY_TITLE);
}