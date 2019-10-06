package org.camunda.bpm.identity.plugins;

import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.impl.GroupQueryImpl;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;

import java.util.List;

/**
 * @author eagle_daiqiang
 * 组查询实现
 */
public class MyGroupQueryImpl extends GroupQueryImpl {

    public MyGroupQueryImpl() {
    }

    public MyGroupQueryImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @Override public long executeCount(CommandContext commandContext) {
        final MyUserIdentityProviderSession identityProvider = getIdentityProvider(commandContext);
        return identityProvider.findGroupCountByQueryCriteria(this);
    }

    @Override public List<Group> executeList(CommandContext commandContext, Page page) {
        final MyUserIdentityProviderSession identityProvider = getIdentityProvider(commandContext);
        return identityProvider.findGroupByQueryCriteria(this);
    }

    protected MyUserIdentityProviderSession getIdentityProvider(CommandContext commandContext) {
        return (MyUserIdentityProviderSession) commandContext.getReadOnlyIdentityProvider();
    }
}
