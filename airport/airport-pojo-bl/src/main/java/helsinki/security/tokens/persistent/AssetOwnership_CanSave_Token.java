package helsinki.security.tokens.persistent;

import static java.lang.String.format;

import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.tokens.Template;
import helsinki.asset.AssetOwnership;
import helsinki.security.tokens.UsersAndPersonnelModuleToken;

/**
 * A security token for entity {@link AssetOwnership} to guard Save.
 *
 * @author Developers
 *
 */
public class AssetOwnership_CanSave_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.SAVE.forTitle(), AssetOwnership.ENTITY_TITLE);
    public final static String DESC = format(Template.SAVE.forDesc(), AssetOwnership.ENTITY_TITLE);
}