package org.jboss.jsr299.tck.tests.context.passivating.broken.decoratorWithNonPassivatingBeanConstructor;

import org.jboss.jsr299.tck.AbstractJSR299Test;
import org.jboss.jsr299.tck.DeploymentError;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.testharness.impl.packaging.ExpectedDeploymentException;
import org.jboss.testharness.impl.packaging.jsr299.BeansXml;
import org.testng.annotations.Test;

@Artifact
@ExpectedDeploymentException(DeploymentError.class)
@SpecVersion(spec="cdi", version="PFD2")
@BeansXml("beans.xml")
public class DecoratorWithNonPassivatingBeanConstructorTest extends AbstractJSR299Test
{
   @Test(groups = { "contexts", "passivation" })
   @SpecAssertion(section = "6.6.4", id = "aag")
   public void testPassivationCapableBeanWithNonPassivatingDecoratorBeanConstructorFails()
   {
      assert false;
   }
}
