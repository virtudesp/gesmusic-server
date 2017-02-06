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
package net.daw.helper.statics;

import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SqlBuilder {

    private static String getFilterExpression(FilterBeanHelper temp) {

        switch (temp.getFilterOperator()) {
            case "like": //like
                return temp.getFilterConnector() + " " + temp.getFilter() + " LIKE '%" + temp.getFilterValue() + "%' ";
            case "nlik": //not like
                return temp.getFilterConnector() + " " + temp.getFilter() + " NOT LIKE '%" + temp.getFilterValue() + "%' ";
            case "star": //starts with
                return temp.getFilterConnector() + " " + temp.getFilter() + " LIKE '" + temp.getFilterValue() + "%' ";
            case "nsta": //not starts with
                return temp.getFilterConnector() + " " + temp.getFilter() + " NOT LIKE '" + temp.getFilterValue() + "%' ";
            case "ends": //ends with
                return temp.getFilterConnector() + " " + temp.getFilter() + " LIKE '%" + temp.getFilterValue() + "' ";
            case "nend": //not ends with
                return temp.getFilterConnector() + " " + temp.getFilter() + " NOT LIKE '%" + temp.getFilterValue() + "' ";
            case "equa": //equal
                return temp.getFilterConnector() + " " + temp.getFilter() + " = " + temp.getFilterValue() + " ";
            case "nequ": //not equal
                return temp.getFilterConnector() + " " + temp.getFilter() + " != " + temp.getFilterValue() + " ";
            case "lowe": //lower than
                return temp.getFilterConnector() + " " + temp.getFilter() + " < " + temp.getFilterValue() + " ";
            case "lequ": //lower or equal than
                return temp.getFilterConnector() + " " + temp.getFilter() + " <= " + temp.getFilterValue() + " ";
            case "grea": //greater than
                return temp.getFilterConnector() + " " + temp.getFilter() + " > " + temp.getFilterValue() + " ";
            case "gequ": //greater or equal than
                return temp.getFilterConnector() + " " + temp.getFilter() + " >= " + temp.getFilterValue() + " ";
            default:
                throw new Error("Filter expression malformed. Operator not valid.");
        }
    }

    public static String buildSqlWhere(ArrayList<FilterBeanHelper> alFilter) {
        String strSQLFilter = "";
        if (alFilter != null) {
            Iterator iterator = alFilter.iterator();
            while (iterator.hasNext()) {
                FilterBeanHelper oFilterBean = (FilterBeanHelper) iterator.next();
                strSQLFilter += getFilterExpression(oFilterBean);
            }
        }
        return strSQLFilter;
    }

    public static String buildSqlOrder(HashMap<String, String> hmOrder) {
        String strSQLOrder = "";
        if (hmOrder != null) {
            strSQLOrder += " ORDER BY";
            String strOrderFieldName;
            for (Map.Entry oPar : hmOrder.entrySet()) {
                strOrderFieldName = (String) oPar.getKey();
                if (strOrderFieldName.length() >= 4) {
                    if ("obj_".equals(strOrderFieldName.substring(0, 4))) {
                        strOrderFieldName = "id_" + strOrderFieldName.substring(4);
                    }
                }
                strSQLOrder += " " + strOrderFieldName + " " + oPar.getValue() + ",";
            }
            strSQLOrder = strSQLOrder.substring(0, strSQLOrder.length() - 1);
        }
        return strSQLOrder;
    }

//    public static String buildSqlLimit(int intTotalRegs, int intRegsPerPage, int intPageNumber) {
//        int maxPaginas = new Double(intTotalRegs / intRegsPerPage).intValue();
//        intPageNumber = Math.min(intPageNumber - 1, maxPaginas) + 1;
//        int intOffset = Math.max(((intPageNumber - 1) * intRegsPerPage), 0);
//        return " LIMIT " + intOffset + " , " + intRegsPerPage;
//    }
    
    public static String buildSqlLimit(Long intTotalRegs, Integer intRegsPerPage, Integer intPageNumber) {
        String SQLLimit = "";
        if (intRegsPerPage > 0 || intRegsPerPage < 10000) {
            if (intPageNumber > 0 || intPageNumber <= (floor(intTotalRegs / intRegsPerPage))) {
                Double maxPaginas = floor(intTotalRegs / intRegsPerPage);
                Integer intMaxPaginas = maxPaginas.intValue();
                Integer intPageNumberMin = min(intPageNumber - 1, intMaxPaginas) + 1;
                Integer intOffset = max(((intPageNumberMin - 1) * intRegsPerPage), 0);
                SQLLimit = " LIMIT " + intOffset + " , " + intRegsPerPage;
            }
        }
        return SQLLimit;
    }

}
