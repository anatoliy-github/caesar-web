package servlets;

import logic.Crypto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartContextListener implements ServletContextListener {
    public static final String UPLOAD_DIR = "uploaded-files";
    public static final String RESULT_DIR = "result-files";
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        LOGGER.debug("Context Listener Initialized");

        String lengthOfAlphabet = String.valueOf(Crypto.getLengthOfAlphabet());
        ctx.setAttribute("length_alphabet", lengthOfAlphabet);
        ctx.setAttribute("upload_dir", UPLOAD_DIR);
        ctx.setAttribute("result_dir", RESULT_DIR);
    }

}
