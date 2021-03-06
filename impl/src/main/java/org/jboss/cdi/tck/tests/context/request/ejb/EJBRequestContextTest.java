/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.cdi.tck.tests.context.request.ejb;

import static org.jboss.cdi.tck.TestGroups.CONTEXTS;
import static org.jboss.cdi.tck.TestGroups.JAVAEE_FULL;

import java.util.concurrent.Future;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.cdi.tck.AbstractTest;
import org.jboss.cdi.tck.Timer;
import org.jboss.cdi.tck.Timer.StopCondition;
import org.jboss.cdi.tck.shrinkwrap.EnterpriseArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecAssertions;
import org.jboss.test.audit.annotations.SpecVersion;
import org.testng.annotations.Test;

/**
 * EJB and related tests with the built-in request context.
 * 
 * Clarification for request context lifecycle during remote method invocation of EJB is required - see CDI-180.
 * 
 * @author David Allen
 * @author Martin Kouba
 */
@Test(groups = JAVAEE_FULL)
@SpecVersion(spec = "cdi", version = "20091101")
public class EJBRequestContextTest extends AbstractTest {

    @Deployment(name = "TEST", order = 1)
    public static EnterpriseArchive createTestArchive() {
        return new EnterpriseArchiveBuilder().withTestClass(EJBRequestContextTest.class)
                .withClasses(FMS.class, FMSModelIII.class, BarBean.class, SimpleRequestBean.class, FooRemote.class).build();
    }

    @Deployment(name = "REMOTE_EJB", order = 2)
    public static EnterpriseArchive createEjbArchive() {
        return new EnterpriseArchiveBuilder().notTestArchive().noDefaultWebModule().withName("test-ejb.ear")
                .withEjbModuleName("test-ejb.jar").withClasses(FooBean.class, FooRemote.class, FooRequestBean.class).build();
    }

    /**
     * The request scope is active during any remote method invocation of any EJB bean, during any call to an EJB timeout method
     * and during message delivery to any EJB message driven bean.
     */
    @OperateOnDeployment("TEST")
    @Test(groups = { CONTEXTS })
    @SpecAssertion(section = "6.7.1", id = "gc")
    public void testRequestScopeActiveDuringCallToEjbTimeoutMethod() throws Exception {
        FMSModelIII.reset();
        FMS flightManagementSystem = getInstanceByType(FMS.class);
        flightManagementSystem.climb();

        new Timer().setDelay(20000l).addStopCondition(new StopCondition() {
            public boolean isSatisfied() {
                return FMSModelIII.isClimbed();
            }
        }).start();

        assert flightManagementSystem.isRequestScopeActive();
    }

    /**
     * The request context is destroyed after the remote method invocation, timeout or message delivery completes.
     */
    @OperateOnDeployment("TEST")
    @Test(groups = { CONTEXTS })
    @SpecAssertion(section = "6.7.1", id = "hc")
    public void testRequestScopeDestroyedAfterCallToEjbTimeoutMethod() throws Exception {
        FMSModelIII.reset();
        SimpleRequestBean.reset();
        FMS flightManagementSystem = getInstanceByType(FMS.class);
        flightManagementSystem.climb();

        Timer timer = new Timer().setDelay(20000l).addStopCondition(new StopCondition() {
            public boolean isSatisfied() {
                return FMSModelIII.isClimbed();
            }
        }).start();

        flightManagementSystem.descend();

        timer.addStopCondition(new StopCondition() {
            public boolean isSatisfied() {
                return FMSModelIII.isDescended();
            }
        }, true).start();

        assert !flightManagementSystem.isSameBean();
        assert SimpleRequestBean.isBeanDestroyed();
    }

    @EJB(lookup = "java:global/test-ejb/test-ejb/FooBean!org.jboss.cdi.tck.tests.context.request.ejb.FooRemote")
    FooRemote foo;

    @EJB
    BarBean bar;

    @OperateOnDeployment("TEST")
    @Test(groups = { CONTEXTS })
    @SpecAssertions({ @SpecAssertion(section = "6.7.1", id = "ga"), @SpecAssertion(section = "6.7.1", id = "ha") })
    public void testRequestScopeActiveDuringRemoteCallToEjb() throws Exception {
        assert foo.ping() != null;
        assert foo.wasRequestBeanInPreviousCallDestroyed();
    }

    @OperateOnDeployment("TEST")
    @Test(groups = { CONTEXTS })
    @SpecAssertions({ @SpecAssertion(section = "6.7.1", id = "gb"), @SpecAssertion(section = "6.7.1", id = "hb") })
    public void testRequestScopeActiveDuringAsyncCallToEjb() throws Exception {
        SimpleRequestBean simpleRequestBean = getInstanceByType(SimpleRequestBean.class);
        SimpleRequestBean.reset();
        Future<Double> result = bar.compute();
        Double id = result.get();
        assert id != -1.00;
        assert id != simpleRequestBean.getId();
        assert SimpleRequestBean.isBeanDestroyed();
    }

}
