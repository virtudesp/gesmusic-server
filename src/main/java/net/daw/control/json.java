/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * sisane-server: Helps you to develop easily AJAX web applications 
 *               by copying and modifying this Java Server.
 *
 * Sources at https://github.com/rafaelaznar/sisane-server
 * 
 * sisane-server is distributed under the MIT License (MIT)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.daw.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import net.daw.bean.implementation.ReplyBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.daw.connection.publicinterface.ConnectionInterface;
import static net.daw.helper.statics.AppConfigurationHelper.getSourceConnection;
import net.daw.helper.statics.ConnectionClassHelper;
import net.daw.helper.statics.EstadoHelper;
import net.daw.helper.statics.EstadoHelper.Tipo_estado;
import net.daw.helper.statics.Log4j;
import net.daw.helper.statics.ParameterCook;
import net.daw.service.publicinterface.ViewServiceInterface;

public class json extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private void sendResponseJson(HttpServletRequest request, HttpServletResponse response, ReplyBean answer) throws ServletException, IOException {
        request.setAttribute("answer", answer);
        getServletContext().getRequestDispatcher("/jsp/messageAjax.jsp").forward(request, response);
    }

    private void sendResponseHtml(HttpServletRequest request, HttpServletResponse response, String strTitle, String strMessage) throws ServletException, IOException {
        request.setAttribute("title", strTitle);
        request.setAttribute("message", strMessage);
        getServletContext().getRequestDispatcher("/jsp/message.jsp").forward(request, response);
    }

    private void retardo(Integer iLast) {
        try {
            Thread.sleep(iLast);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        Log4j.infoLog(this.getClass().getName() + ": " + request.getMethod() + " request: " + request.getRequestURL().append('?').append(request.getQueryString()));
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        //response.setHeader("Access-Control-Allow-Methods", "PATCH, POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, x-requested-with, Content-Type");
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (Exception ex) {
                ReplyBean oReplyBean = new ReplyBean(500, "sisane-server error. Please, contact your administrator.");
                sendResponseJson(request, response, oReplyBean);
                Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
                return;
            }
            if (EstadoHelper.getTipo_estado() == Tipo_estado.Debug) {
                retardo(EstadoHelper.getDelay());    //optional debug delay
            }
            String ob = ParameterCook.prepareObject(request);
            String op = ParameterCook.prepareOperation(request);
            if ("".equals(op) && "".equals(ob)) {
                Connection oConnection = null;
                ConnectionInterface oDataConnectionSource = null;
                try {
                    oDataConnectionSource = getSourceConnection();
                    oConnection = oDataConnectionSource.newConnection();
                    if (oConnection.isValid(10)) {
                        sendResponseHtml(request, response, "sisane-server by rafael aznar", "<p>the server is up and running on " + request.getLocalName() + ":" + request.getLocalPort() + "</p><p>Database name: " + ConnectionClassHelper.getDatabaseName() + "</p><p>Database access OK</p>");
                    } else {
                        sendResponseHtml(request, response, "sisane-server by rafael aznar", "<p>the server is up and running on " + request.getLocalName() + ":" + request.getLocalPort() + "</p><p>Database name: " + ConnectionClassHelper.getDatabaseName() + "</p><p>Database access timeout KO</p>");
                    }
                } catch (Exception ex) {
                    sendResponseHtml(request, response, "sisane-server by rafael aznar", "the server is up and running on " + request.getLocalName() + ":" + request.getLocalPort() + "</p><p>Database name: " + ConnectionClassHelper.getDatabaseName() + "</p><p>Database access KO</p>");
                } finally {
                    if (oConnection != null) {
                        oConnection.close();
                    }
                    if (oDataConnectionSource != null) {
                        oDataConnectionSource.disposeConnection();
                    }
                }
            } else {
                try {
                    String strClassName = "net.daw.service.implementation." + ParameterCook.prepareCamelCaseObject(request) + "Service";
                    ViewServiceInterface oService = (ViewServiceInterface) Class.forName(strClassName).getDeclaredConstructor(HttpServletRequest.class).newInstance(request);
                    Method oMethodService = oService.getClass().getMethod(ParameterCook.prepareOperation(request));
                    ReplyBean oResult = (ReplyBean) oMethodService.invoke(oService);
                    sendResponseJson(request, response, oResult);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    ReplyBean oReplyBean = new ReplyBean(500, "sisane-server error. Please, contact your administrator.");
                    sendResponseJson(request, response, oReplyBean);
                    Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
                }
            }
        } catch (ServletException | IOException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            ReplyBean oReplyBean = new ReplyBean(500, "sisane-server error. Please, contact your administrator.");
            sendResponseJson(request, response, oReplyBean);
            Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {

        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "sisane-server Servlet";
    }// </editor-fold>
}
