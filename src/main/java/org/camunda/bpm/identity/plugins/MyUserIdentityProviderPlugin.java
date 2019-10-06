package org.camunda.bpm.identity.plugins;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.identity.impl.ldap.util.CertificateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author eagle_daiqiang
 * 用户集成插件
 */
public class MyUserIdentityProviderPlugin extends MyUserIdentityConfigration implements ProcessEnginePlugin {
    private final static Logger logger = LoggerFactory.getLogger(MyUserIdentityProviderPlugin.class);

    protected boolean acceptUntrustedCertificates = true;

    @Override public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        logger.info("Identity Provider Plugin[MyUserIdentityProviderPlugin] starts to init. ");
        if(this.acceptUntrustedCertificates){
            logger.info("untrusted Certificates is allowed for IdentityProvider.");
            CertificateHelper.acceptUntrusted();
        }

        //init IdentityProviderFactory to processEngineConfiguration.
        MyUserIdentityProviderFactory myUserIdentityProviderFactory = new MyUserIdentityProviderFactory(this);
        processEngineConfiguration.setIdentityProviderSessionFactory(myUserIdentityProviderFactory);

    }

    @Override public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        // nothing to do
    }

    @Override public void postProcessEngineBuild(ProcessEngine processEngine) {
        // nothing to do
    }

    public boolean isAcceptUntrustedCertificates() {
        return acceptUntrustedCertificates;
    }

    public void setAcceptUntrustedCertificates(boolean acceptUntrustedCertificates) {
        this.acceptUntrustedCertificates = acceptUntrustedCertificates;
    }
}
