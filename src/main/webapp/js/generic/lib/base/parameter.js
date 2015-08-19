/* 
 * Copyright (C) 2015 rafa
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

baseModule.prototype.getUrlObjectFromParamsWithoutParamArray = function (urlObj, nameParameterArray) {
    var newUrlObj = jQuery.extend(true, {}, urlObj); //http://stackoverflow.com/questions/122102/what-is-the-most-efficient-way-to-clone-an-object
    $.each(nameParameterArray, function () {
        delete newUrlObj[this];
    })
    return newUrlObj;
}
baseModule.prototype.getUrlStringFromParamsObject = function (urlObj) {
    var result = "";
    for (var key in urlObj) {
        result += "&" + key + "=" + urlObj[key];
    }
    return result.substring(1);
}
baseModule.prototype.printOrderParamsInUrl = function (objParams) {
    if (objParams)
        if (objParams.order) {
            return '&order=' + objParams.order + '&ordervalue=' + objParams.ordervalue;
        } else {
            return '';
        }
}
baseModule.prototype.printFilterParamsInUrl = function (objParams) {
    if (objParams)
        if (objParams.filter) {
            return "&filter=" + objParams.filter + "&filteroperator=" + objParams.filteroperator + "&filtervalue=" + objParams.filtervalue;
        } else {
            return '';
        }
}
baseModule.prototype.printSystemFilterParamsInUrl = function (objParams) {
    if (objParams)
        if (objParams.systemfilter) {
            return "&systemfilter=" + this.objParams.systemfilter + "&systemfilteroperator=" + this.objParams.systemfilteroperator + "&systemfiltervalue=" + this.objParams.systemfiltervalue;
        } else {
            return '';
        }
};