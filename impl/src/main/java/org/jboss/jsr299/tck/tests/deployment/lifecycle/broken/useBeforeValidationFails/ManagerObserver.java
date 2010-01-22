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
package org.jboss.jsr299.tck.tests.deployment.lifecycle.broken.useBeforeValidationFails;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;

class ManagerObserver
{
   private static boolean managerInitialized = false;
   private static boolean managerDeployed = false;
   
   public void managerInitialized(@Observes AfterBeanDiscovery event, BeanManager beanManager)
   {
      managerInitialized = true;
      beanManager.fireEvent("An event that should not be fired");
   }

   public void managerDeployed(@Observes AfterDeploymentValidation event, BeanManager beanManager)   {
      managerDeployed = true;
   }

   public static boolean isManagerInitialized()
   {
      return managerInitialized;
   }

   public static void setManagerInitialized(boolean managerInitialized)
   {
      ManagerObserver.managerInitialized = managerInitialized;
   }

   public static boolean isManagerDeployed()
   {
      return managerDeployed;
   }

   public static void setManagerDeployed(boolean managerDeployed)
   {
      ManagerObserver.managerDeployed = managerDeployed;
   }
}
