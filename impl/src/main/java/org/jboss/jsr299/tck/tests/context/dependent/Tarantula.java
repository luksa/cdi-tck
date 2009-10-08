package org.jboss.jsr299.tck.tests.context.dependent;

import javax.annotation.PreDestroy;

import org.jboss.jsr299.tck.impl.JSR299ConfigurationImpl;

class Tarantula extends Spider implements DeadlySpider
{

   private static boolean destroyed;
   
   private static boolean dependentContextActive = false;
   
   public Tarantula()
   {
      dependentContextActive = JSR299ConfigurationImpl.get().getContexts().getDependentContext().isActive();
   }
   
   @PreDestroy
   public void preDestroy()
   {
      destroyed = true;
   }
   
   public static boolean isDependentContextActive()
   {
      return dependentContextActive;
   }
   
   public static boolean isDestroyed()
   {
      return destroyed;
   }
   
   public static void reset()
   {
      destroyed = false;
      dependentContextActive = false;
   }
   
   
}
