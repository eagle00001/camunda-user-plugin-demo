package org.camunda.bpm.identity.plugins;

import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.UserQueryImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;

import java.util.List;

/**
 * @author eagle_daiqiang
 */
public class MyUserQueryImpl extends UserQueryImpl {

    public MyUserQueryImpl() {
        super();
    }

    public MyUserQueryImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @Override public long executeCount(CommandContext commandContext) {
        final MyUserIdentityProviderSession provider = getIdentityProvider(commandContext);
        return provider.findUserCountByQueryCriteria(this);
    }

    @Override public List<User> executeList(CommandContext commandContext, Page page) {
        final MyUserIdentityProviderSession provider = getIdentityProvider(commandContext);
        return provider.findUserByQueryCriteria(this);
    }


    protected MyUserIdentityProviderSession getIdentityProvider(CommandContext commandContext) {
        return (MyUserIdentityProviderSession) commandContext.getReadOnlyIdentityProvider();
    }
}
