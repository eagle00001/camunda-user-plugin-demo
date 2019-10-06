package org.camunda.bpm.identity.plugins;

import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;

/**
 * @author eagle_daiqiang
 * 用户服务工厂类
 */
public class MyUserIdentityProviderFactory implements SessionFactory {
    private MyUserIdentityConfigration myUserIdentityConfigration;

    public MyUserIdentityProviderFactory(MyUserIdentityConfigration myUserIdentityConfigration) {
        this.myUserIdentityConfigration = myUserIdentityConfigration;
    }

    @Override public Class<?> getSessionType() {
        return ReadOnlyIdentityProvider.class;
    }

    @Override public Session openSession() {
        return new MyUserIdentityProviderSession(this.myUserIdentityConfigration);
    }

    public MyUserIdentityConfigration getMyUserIdentityConfigration() {
        return myUserIdentityConfigration;
    }

}
