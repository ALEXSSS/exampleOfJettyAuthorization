package main;

import accounts.AccountService;
import dbService.DBService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServelets;
import servlets.SignUpServelets;

/**
 * @author v.chibrikov
 * @author A.Luchko
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 *         <p>
 *         доделано и протестировано A.Luchko
 */
public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBService();
        AccountService accountService = new AccountService();

//        accountService.addNewUserToLoginMap(new UserProfile("admin"));
//        accountService.addNewUserToLoginMap(new UserProfile("test"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignUpServelets(accountService, dbService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServelets(accountService, dbService)), "/signin");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
