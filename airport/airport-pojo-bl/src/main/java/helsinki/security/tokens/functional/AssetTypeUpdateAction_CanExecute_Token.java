package helsinki.security.tokens.functional;

import static java.lang.String.format;

import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.tokens.Template;
import helsinki.asset.actions.AssetTypeUpdateAction;
import helsinki.security.tokens.UsersAndPersonnelModuleToken;

/**
 * A security token for entity {@link AssetTypeUpdateAction} to guard Execute.
 *
 * @author Developers
 *
 */
public class AssetTypeUpdateAction_CanExecute_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.EXECUTE.forTitle(), AssetTypeUpdateAction.ENTITY_TITLE);
    public final static String DESC = format(Template.EXECUTE.forDesc(), AssetTypeUpdateAction.ENTITY_TITLE);
}