/*
 * Copyright (c) 2016 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * sisane-server: Helps you to develop easily AJAX web applications 
 *                   by copying and modifying this Java Server.
 *
 * Sources at https://github.com/rafaelaznar/sisane-server
 * 
 * sisane-server is distributed under the MIT License (MIT)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated posologiaation files (the "Software"), to deal
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

http://localhost:8081/sisane-server/json?ob=user&op=login&user=rafael&pass=79063E8037FFF16D297A1FE65136F1251126CDDB2CC9870ECF8D653835538E85

http://localhost:8081/sisane-server/json?ob=posologia&op=getall

http://localhost:8081/sisane-server/json?ob=posologia&op=get&id=2

http://localhost:8081/sisane-server/json?ob=posologia&op=set&json={"descripcion":"nsnc"}



 */
package net.daw.service.implementation;

import com.google.gson.Gson;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.implementation.PosologiaBean;
import net.daw.bean.implementation.PusuarioBean;
import net.daw.bean.implementation.ReplyBean;
import net.daw.bean.implementation.UsuarioBean;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.implementation.PosologiaDao;
import net.daw.helper.statics.AppConfigurationHelper;
import static net.daw.helper.statics.AppConfigurationHelper.getSourceConnection;
import net.daw.helper.statics.FilterBeanHelper;
import net.daw.helper.statics.JsonMessage;
import net.daw.helper.statics.Log4j;
import net.daw.helper.statics.ParameterCook;
import net.daw.service.publicinterface.TableServiceInterface;
import net.daw.service.publicinterface.ViewServiceInterface;

public class PosologiaService implements TableServiceInterface, ViewServiceInterface {

    protected HttpServletRequest oRequest = null;

    public PosologiaService(HttpServletRequest request) {
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
            String data = null;
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.getFilterParams(ParameterCook.prepareFilter(oRequest));
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                PosologiaDao oPosologiaDao = new PosologiaDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                data = JsonMessage.getJsonExpression(200, Long.toString(oPosologiaDao.getCount(alFilter)));
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
                PosologiaDao oPosologiaDao = new PosologiaDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                PosologiaBean oPosologiaBean = new PosologiaBean(id);
                oPosologiaBean = oPosologiaDao.get(oPosologiaBean, AppConfigurationHelper.getJsonMsgDepth());
                Gson gson = AppConfigurationHelper.getGson();
                data = JsonMessage.getJsonExpression(200, AppConfigurationHelper.getGson().toJson(oPosologiaBean));
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
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(ParameterCook.prepareOrder(oRequest));
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.getFilterParams(ParameterCook.prepareFilter(oRequest));
            String data = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                PosologiaDao oPosologiaDao = new PosologiaDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                ArrayList<PosologiaBean> arrBeans = oPosologiaDao.getAll(alFilter, hmOrder, AppConfigurationHelper.getJsonMsgDepth());
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
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(ParameterCook.prepareOrder(oRequest));
            ArrayList<FilterBeanHelper> alFilter = ParameterCook.getFilterParams(ParameterCook.prepareFilter(oRequest));
            String data = null;
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                PosologiaDao oPosologiaDao = new PosologiaDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                List<PosologiaBean> arrBeans = oPosologiaDao.getPage(intRegsPerPag, intPage, alFilter, hmOrder, AppConfigurationHelper.getJsonMsgDepth());
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
                PosologiaDao oPosologiaDao = new PosologiaDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                data = JsonMessage.getJsonExpression(200, (String) oPosologiaDao.remove(id).toString());
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
            ReplyBean oReplyBean = new ReplyBean();
            Connection oConnection = null;
            ConnectionInterface oDataConnectionSource = null;
            try {
                oDataConnectionSource = getSourceConnection();
                oConnection = oDataConnectionSource.newConnection();
                oConnection.setAutoCommit(false);
                PosologiaDao oPosologiaDao = new PosologiaDao(oConnection, (PusuarioBean) oRequest.getSession().getAttribute("userBean"), null);
                PosologiaBean oPosologiaBean = new PosologiaBean();
                oPosologiaBean = AppConfigurationHelper.getGson().fromJson(jason, oPosologiaBean.getClass());
                if (oPosologiaBean != null) {
                    Integer iResult = oPosologiaDao.set(oPosologiaBean);
                    if (iResult >= 1) {
                        oReplyBean.setCode(200);
                        oReplyBean.setJson(JsonMessage.getJsonExpression(200, iResult.toString()));
                    } else {
                        oReplyBean.setCode(500);
                        oReplyBean.setJson(JsonMessage.getJsonMsg(500, "Error during registry set"));
                    }
                } else {
                    oReplyBean.setCode(500);
                    oReplyBean.setJson(JsonMessage.getJsonMsg(500, "Error during registry set"));
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
