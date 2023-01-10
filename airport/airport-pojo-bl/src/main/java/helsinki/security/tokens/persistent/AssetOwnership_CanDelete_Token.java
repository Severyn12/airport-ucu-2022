package helsinki.security.tokens.persistent;

import static java.lang.String.format;

import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.tokens.Template;
import helsinki.asset.AssetOwnership;
import helsinki.security.tokens.UsersAndPersonnelModuleToken;

/**
 * A security token for entity {@link AssetOwnership} to guard Delete.
 *
 * @author Developers
 *
 */
public class AssetOwnership_CanDelete_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.DELETE.forTitle(), AssetOwnership.ENTITY_TITLE);
    public final static String DESC = format(Template.DELETE.forDesc(), AssetOwnership.ENTITY_TITLE);
}