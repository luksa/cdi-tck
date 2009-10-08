package org.jboss.jsr299.tck.tests.context.passivating.broken.enterpriseBeanWithNonPassivatingInitializerParameterInInterceptor;

import org.jboss.jsr299.tck.AbstractJSR299Test;
import org.jboss.jsr299.tck.DeploymentError;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.testharness.impl.packaging.ExpectedDeploymentException;
import org.jboss.testharness.impl.packaging.Packaging;
import org.jboss.testharness.impl.packaging.PackagingType;
import org.testng.annotations.Test;

@Artifact
@Packaging(PackagingType.EAR)
@ExpectedDeploymentException(DeploymentError.class)
@SpecVersion(spec="cdi", version="PFD2")
public class EnterpriseBeanWithNonPassivatingInitializerParameterInInterceptorTest extends AbstractJSR299Test
{   
   @Test(groups = { "contexts", "passivation", "ri-broken" })
   @SpecAssertion(section = "6.6.4", id = "bdc")
   //WBRI-361
   public void testSessionBeanWithNonPassivatingInitializerParamInInterceptorFails()
   {
      assert false;
   }
   
}
