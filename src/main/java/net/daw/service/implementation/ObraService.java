/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * bauxer server: Helps you to develop easily AJAX web applications 
 *                   by copying and modifying this Java Server.
 *
 * Sources at https://github.com/rafaelaznar/bauxer
 * 
 * bauxer server is distributed under the MIT License (MIT)
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
package net.daw.service.implementation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.implementation.ActoBean;
import net.daw.bean.implementation.ObraBean;
import net.daw.bean.implementation.ReplyBean;
import net.daw.bean.implementation.PusuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.implementation.ObraDao;
import net.daw.helper.statics.AppConfigurationHelper;
import static net.daw.helper.statics.AppConfigurationHelper.getSourceConnection;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.JsonMessage;
import net.daw.helper.statics.Log4j;
import net.daw.helper.statics.ParameterCook;
import net.daw.service.publicinterface.TableServiceInterface;
import net.daw.service.publicinterface.ViewServiceInterface;

public class ObraService implements TableServiceInterface, ViewServiceInterface {

    protected HttpServletRequest oRequest = null;

    public ObraService(HttpServletRequest request) {
        oRequest = request;
    }

    private Boolean checkpermission(String strMethodName) throws Exception {
        PusuarioBean oPusuarioBean = (PusuarioBean) oRequest.getSession().getAttribute("userBean");
        if (oPusuarioBean != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ReplyBean getcount() throws Exception {
        if (this.checkpermission("getcount")) {
            // parámetros añadidos
            int idCompositor = ParameterCook.prepareId(oRequest);
            int idActo = ParameterCook.prepareForeignId(oRequest);
            int idAgrupacion = ParameterCook.prepareForeignId2(oRequest);
            int idObra = ParameterCook.prepareForeignId3(oRequest);
            String data = null;
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.getFilterParams(ParameterCook.prepareFilter(oRequest));
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                ObraDao oObraDao = new ObraDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                // Si no hay idCompositor el método es de obra 
                if (idCompositor == 0) {
                    if(idObra == 0){
                        data = JsonMessage.getJsonExpression(200, Long.toString(oObraDao.getCount(alFilter)));
                    }
                    // Si hay idObra es para obtener el historial
                    else{
                        data = JsonMessage.getJsonExpression(200, Long.toString(oObraDao.getCountXHistorial(idObra, alFilter)));
                    }
                } else {
                    // Si no hay id_acto es un método de obrasxcompositor
                    if (idActo == 0) {
                        data = JsonMessage.getJsonExpression(200, Long.toString(oObraDao.getCountXCompositor(idCompositor, alFilter)));
                    } else {
                        // Es un método para obtener las obras del repertorio
                        data = JsonMessage.getJsonExpression(200, Long.toString(oObraDao.getCountXRepertorio(idActo, idAgrupacion, alFilter)));
                    }
                }
            } catch (Exception ex) {
                Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
                throw new Exception();
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return new ReplyBean(200, data);
        } else {
            return new ReplyBean(401, JsonMessage.getJsonMsg(401, "Unauthorized"));
        }
    }

    @Override
    public ReplyBean get() throws Exception {
        if (this.checkpermission("get")) {
            int id = ParameterCook.prepareId(oRequest);
            String data = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                ObraDao oObraDao = new ObraDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                ObraBean oObraBean = new ObraBean(id);
                oObraBean = oObraDao.get(oObraBean, AppConfigurationHelper.getJsonMsgDepth());
                Gson gson = AppConfigurationHelper.getGson();
                data = JsonMessage.getJsonExpression(200, AppConfigurationHelper.getGson().toJson(oObraBean));
            } catch (Exception ex) {
                Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
                throw new Exception();
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return new ReplyBean(200, data);
        } else {
            return new ReplyBean(401, JsonMessage.getJsonMsg(401, "Unauthorized"));
        }
    }

    @Override
    public ReplyBean getall() throws Exception {
        if (this.checkpermission("getall")) {
            // parámetro añadido
            int idCompositor = ParameterCook.prepareForeignId(oRequest);
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(ParameterCook.prepareOrder(oRequest));
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.getFilterParams(ParameterCook.prepareFilter(oRequest));
            String data = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                ObraDao oObraDao = new ObraDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                // Si no hay idCompositor el método es de obra y si hay idCompositor es de obrasxcompositor
                ArrayList<ObraBean> arrBeans;
                if (idCompositor == 0) {
                    arrBeans = oObraDao.getAll(alFilter, hmOrder, AppConfigurationHelper.getJsonMsgDepth());
                } else {
                    arrBeans = oObraDao.getAllXCompositor(idCompositor, alFilter, hmOrder, AppConfigurationHelper.getJsonMsgDepth());
                }
                data = JsonMessage.getJsonExpression(200, AppConfigurationHelper.getGson().toJson(arrBeans));
            } catch (Exception ex) {
                Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
                throw new Exception();
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return new ReplyBean(200, data);
        } else {
            return new ReplyBean(401, JsonMessage.getJsonMsg(401, "Unauthorized"));
        }
    }

    @Override
    public ReplyBean getpage() throws Exception {
        if (this.checkpermission("getpage")) {
            int intRegsPerPag = ParameterCook.prepareRpp(oRequest);
            int intPage = ParameterCook.preparePage(oRequest);
            // parámetros añadidos
            int idCompositor = ParameterCook.prepareId(oRequest);
            int idActo = ParameterCook.prepareForeignId(oRequest);
            int idAgrupacion = ParameterCook.prepareForeignId2(oRequest);
            int idObra = ParameterCook.prepareForeignId3(oRequest);
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(ParameterCook.prepareOrder(oRequest));
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.getFilterParams(ParameterCook.prepareFilter(oRequest));
            String data = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                ObraDao oObraDao = new ObraDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                // Si no hay idCompositor el método es de obra y si hay idCompositor es de obrasxcompositor
                List<ObraBean> arrBeans;
                List<ActoBean> arrBeans2; // Para el historial de actos en los que se ha tocado la obra
                if (idCompositor == 0) {
                    arrBeans = oObraDao.getPage(intRegsPerPag, intPage, alFilter, hmOrder, AppConfigurationHelper.getJsonMsgDepth());
                    arrBeans2 = oObraDao.getPageXHistorial(idObra, intRegsPerPag, intPage, alFilter, hmOrder, AppConfigurationHelper.getJsonMsgDepth());
                } else {
                    // Si no hay id_acto es un método de obrasxcompositor
                    if (idActo == 0) {
                    arrBeans = oObraDao.getPageXCompositor(idCompositor, intRegsPerPag, intPage, alFilter, hmOrder, AppConfigurationHelper.getJsonMsgDepth());
                    } else{
                        // Es un método para obtener las obras del repertorio
                        arrBeans = oObraDao.getPageXRepertorio(idActo, idAgrupacion, intRegsPerPag, intPage, alFilter, hmOrder, AppConfigurationHelper.getJsonMsgDepth());
                    }
                }
                data = JsonMessage.getJsonExpression(200, AppConfigurationHelper.getGson().toJson(arrBeans));
            } catch (Exception ex) {
                Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
                throw new Exception();
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return new ReplyBean(200, data);
        } else {
            return new ReplyBean(401, JsonMessage.getJsonMsg(401, "Unauthorized"));
        }
    }

    @Override
    public ReplyBean remove() throws Exception {
        if (this.checkpermission("remove")) {
            Integer id = ParameterCook.prepareId(oRequest);
            String data = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                oConnection.setAutoCommit(false);
                ObraDao oObraDao = new ObraDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                data = JsonMessage.getJsonExpression(200, (String) oObraDao.remove(id).toString());
                oConnection.commit();
            } catch (Exception ex) {
                if (oConnection != null) {
                    oConnection.rollback();
                }
                Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
                throw new Exception();
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return new ReplyBean(200, data);
        } else {
            return new ReplyBean(401, JsonMessage.getJsonMsg(401, "Unauthorized"));
        }
    }

    @Override
    public ReplyBean set() throws Exception {
        if (this.checkpermission("set")) {
            String jason = ParameterCook.prepareJson(oRequest);
            JsonParser parser = new JsonParser();
            JsonElement elementObject = parser.parse(jason);
            // Se necesita el id para diferenciar un insert de un update enviando como parámetro where al crear oObraBean             
            Integer id = ParameterCook.prepareId(oRequest);
            // Parámetro añadido para relaciones 1:n
            int idCompositor = ParameterCook.prepareForeignId(oRequest);
            String where = "";
            if (id == 0) {
                where = null; // para insertar una nueva obra
            } else {
                where += " where id=" + id; // para modificar una obra 
            }
            // hasta aquí lo que he añadido yo

            ReplyBean oReplyBean = new ReplyBean();
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                oConnection.setAutoCommit(false);
                ObraDao oObraDao = new ObraDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), where);
                ObraBean oObraBean;
                // Se crea el objeto según los parámetros que hay
                if (idCompositor == 0) {
                    // para obra
                    if (id == 0) {
                        oObraBean = new ObraBean(); // nueva obra
                    } else {
                        oObraBean = new ObraBean(id); // actualizar una obra 
                    }
                } else {
                    // para obrasxcompositor
                    if (id == 0) {
                        oObraBean = new ObraBean(idCompositor, true); // crear una obra del compositor idCompositor
                    } else {
                        oObraBean = new ObraBean(id, idCompositor, true); // actualizar una obra  del compositor idCompositor
                    }
                }
                oObraBean = AppConfigurationHelper.getGson().fromJson(jason, oObraBean.getClass());
                if (oObraBean != null) {
                    Integer iResult;
                    // Si no hay idCompositor el método es de obra y si hay idCompositor es de obrasxcompositor
                    if (idCompositor == 0) {
                        iResult = oObraDao.set(oObraBean);
                    } else {
                        iResult = oObraDao.setXCompositor(oObraBean, idCompositor);
                    }

                    if (iResult >= 1) {
                        oReplyBean.setCode(200);
                        oReplyBean.setJson(JsonMessage.getJsonExpression(200, iResult.toString()));
                    } else {
                        oReplyBean.setCode(500);
                        oReplyBean.setJson(JsonMessage.getJsonMsg(500, "Error during registry set1"));
                    }
                } else {
                    oReplyBean.setCode(500);
                    oReplyBean.setJson(JsonMessage.getJsonMsg(500, "Error during registry set2"));
                }
                oConnection.commit();
            } catch (Exception ex) {
                if (oConnection != null) {
                    oConnection.rollback();
                }
                Log4j.errorLog(this.getClass().getName() + ":" + (ex.getStackTrace()[0]).getMethodName(), ex);
                throw new Exception();
            } finally {
                if (oConnection != null) {
                    oConnection.close();
                }
                if (oDataConnectionSource != null) {
                    oDataConnectionSource.disposeConnection();
                }
            }
            return oReplyBean;
        } else {
            return new ReplyBean(401, JsonMessage.getJsonMsg(401, "Unauthorized"));
        }
    }

}
