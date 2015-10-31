package de.rfelgent.education;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Of course you can put an "index.html" in the "webapp" directory.
 *
 * This is just a demonstration of coexistence of jsf and plain servlet
 * within one webapp!
 */
@WebServlet(urlPatterns = { "/IndexServlet", "/index.html" })
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.print("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Index of DEMO</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "<a href=\"login.xhtml\">Click for JSF login example</a>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html> ");
        }
    }
}
