package com.SIMRacingApps.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.SIMRacingApps.Server;

/**
 * This class implements the "/iRacing" interface for the HTTP protocol.
 * It serves as a proxy to the internal iRacing server so clients can call it from external devices.
 * It simply passes the portion of the URL that comes after /iRacing to the iRacing server.
 * What gets returned is defined by iRacing, so consult their documentation as needed.
 *
 * @author Jeffrey Gilliam
 * @copyright Copyright (C) 2015 - 2020 Jeffrey Gilliam
 * @license Apache License 2.0
 * @since 1.1
 */
@WebServlet(description = "Returns the requested resource doGet()", urlPatterns = {"/iRacing/*"}, loadOnStartup = 0)
public class iRacing extends HttpServlet {

    private static final long serialVersionUID = 5554696825559093720L;
    private static final int IRACING_PORT = 32034;

    public iRacing() {
    }

    public void init(ServletConfig config) throws ServletException {
        Server.logger().info("init() called");
        super.init(config);
        config.getServletContext();
    }

    public void distroy() throws ServletException {
        Server.logger().info("distroy() called");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (Server.isLogLevelFine()) {
            Server.logger().fine(String.format("doGet(%s) called", request.getRequestURL()));
        }

        String path = request.getRequestURI().replace("/SIMRacingApps/iRacing/", "");
        String hostName = Server.getServerHost();
        if (!"".equals(path)) {
            String requestUrl = "http://" + hostName + ":" + IRACING_PORT + "/" + path + "?" + request.getQueryString() + "&nocache=" + System.currentTimeMillis();

            if (Server.isLogLevelFine()) {
                Server.logger().fine(String.format("doGet(%s) calling iRacing server %s", request.getRequestURL(), path));
            }

            URL url = new URL(requestUrl);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("Referer", "http://members.iracing.com/");

            try {
                InputStream is = urlc.getInputStream();

                if (is != null) {

                    ServletOutputStream out = response.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = is.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    is.close();
                    out.flush();
                    return;
                }
            } catch (IOException e) {
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getOutputStream().println(path + " not found");
    }
}
