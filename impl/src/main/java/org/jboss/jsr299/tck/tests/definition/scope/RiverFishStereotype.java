package org.jboss.jsr299.tck.tests.definition.scope;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.stereotype.Stereotype;

@Stereotype
@Target( { TYPE })
@Retention(RUNTIME)
@ApplicationScoped
@interface RiverFishStereotype
{

}
