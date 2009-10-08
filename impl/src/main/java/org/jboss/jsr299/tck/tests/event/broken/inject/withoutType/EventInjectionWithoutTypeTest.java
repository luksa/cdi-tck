/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.jsr299.tck.tests.event.broken.inject.withoutType;


import org.jboss.jsr299.tck.AbstractJSR299Test;
import org.jboss.jsr299.tck.DefinitionError;
import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.testharness.impl.packaging.ExpectedDeploymentException;
import org.testng.annotations.Test;

/**
 * Tests if the Event object on an injection point annotated
 * {@link @Any} has a type parameter for the actual type of event.
 * 
 * @author David Allen
 */
@Artifact
@ExpectedDeploymentException(DefinitionError.class)
public class EventInjectionWithoutTypeTest extends AbstractJSR299Test
{
   @Test(groups = { "events" })
   // FIXME need spec assertion, probably in the section that defines the Event interface
   public void testAnyAnnotationOnEventInjectionPointWithoutTypeParameterFails()
   {
      assert false;
   }

}
