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
package org.jboss.cdi.tck.tests.lookup.manager.web;

import java.io.IOException;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
@SuppressWarnings("serial")
public class BravoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BeanManager manager = (BeanManager) req.getServletContext().getAttribute(BeanManager.class.getName());
        BeanManager listenerManager = (BeanManager) req.getServletContext().getAttribute(VerifyingListener.class.getName());

        resp.getWriter().append(getFoo(manager).ping());
        resp.getWriter().append(",");
        resp.getWriter().append(getFoo(listenerManager).ping());

        resp.setContentType("text/plain");
    }

    private Foo getFoo(BeanManager manager) {
        Bean<?> bean = manager.resolve(manager.getBeans(Foo.class));
        return (Foo) manager.getReference(bean, Foo.class, manager.createCreationalContext(null));
    }
}
