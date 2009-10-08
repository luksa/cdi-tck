package org.jboss.jsr299.tck.tests.lookup.injection.non.contextual;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet
{
   @Inject
   private Sheep sheep;
   private boolean injectionPerformedCorrectly = false;
   
   private static final long serialVersionUID = -7672096092047821010L;

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      if (req.getRequestURI().endsWith("Servlet")) {
         testServlet(req, resp);
      } else if (req.getRequestURI().endsWith("ServletListener")) {
         testListener(req, resp);
      } else if (req.getRequestURI().endsWith("TagLibraryListener")) {
         testTagLibraryListener(req, resp);
      } else {
         resp.setStatus(404);
      }
   }
   
   private void testServlet(HttpServletRequest req, HttpServletResponse resp) {
   // Return 200 if injection into Servlet occurred, 500 otherwise
      resp.setStatus(injectionPerformedCorrectly ? 200 : 500);
   }
   
   private void testListener(HttpServletRequest req, HttpServletResponse resp) {
   // Return 200 if injection into Listener occurred, 500 otherwise
      boolean result = (Boolean) req.getSession().getServletContext().getAttribute("listener.injected");
      resp.setStatus((result) ? 200 : 500);
   }
   
   private void testTagLibraryListener(HttpServletRequest req, HttpServletResponse resp) {
      // Return 200 if injection into TagLibrary Listener occurred, 500 otherwise
      boolean result = (Boolean) req.getSession().getServletContext().getAttribute("tag.library.listener.injected");
      resp.setStatus((result) ? 200 : 500);
   }

   @Override
   public void init() throws ServletException
   {
      injectionPerformedCorrectly = sheep != null;
   }

   @Override
   public void init(ServletConfig config) throws ServletException
   {
      init();
   }
}
