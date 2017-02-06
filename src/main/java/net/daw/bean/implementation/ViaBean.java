
package net.daw.bean.implementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.daw.bean.publicinterface.GenericBean;
import net.daw.dao.implementation.MedicamentoDao;
import net.daw.helper.statics.EncodingUtilHelper;


/**
 *
 * @author a044888329b
 */
public class ViaBean implements GenericBean{
    
   @Expose
    private Integer id = 0;
   @Expose
    private String descripcion;
   @Expose(serialize = false)
    private Integer id_medicamento = 0;
    @Expose(deserialize = false)
    private MedicamentoBean obj_medicamento;
    
    
    public ViaBean() {
        
    }
    
    public ViaBean(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId_medicamento() {
        return id_medicamento;
    }

    public void setId_medicamento(Integer id_medicamento) {
        this.id_medicamento = id_medicamento;
    }

    public MedicamentoBean getObj_medicamento() {
        return obj_medicamento;
    }

    public void setObj_medicamento(MedicamentoBean obj_medicamento) {
        this.obj_medicamento = obj_medicamento;
    }
    
    
    
    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "descripcion,";
        strColumns += "id_medicamento";
        return strColumns;
    }
    
    
    
    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += id + ",";
        strColumns += EncodingUtilHelper.quotate(descripcion) + ",";
        strColumns += id_medicamento;
        return strColumns;
    }
    
    
    
    @Override
    public String toPairs() {
        String strColumns = "";
//        strColumns +="id=" + id + ",";
        strColumns += "descripcion=" + EncodingUtilHelper.quotate(descripcion) + ",";
        strColumns += "id_medicamento=" + id_medicamento;
        return strColumns;
    }
    
    
    @Override
    public ViaBean fill(ResultSet oResultSet, Connection pooledConnection, PusuarioBean oPusuarioBean_security, Integer expand) throws SQLException, Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDescripcion(oResultSet.getString("descripcion"));
        if (expand > 0) {
            MedicamentoBean oMedicamentoBean = new MedicamentoBean();
            MedicamentoDao oMedicamentoDao = new MedicamentoDao(pooledConnection, oPusuarioBean_security, null);
            oMedicamentoBean.setId(oResultSet.getInt("id_medicamento"));
            oMedicamentoBean = oMedicamentoDao.get(oMedicamentoBean, expand - 1);
            this.setObj_medicamento(oMedicamentoBean);
        } else {
            this.setId_medicamento(oResultSet.getInt("id_medicamento"));
        }
        return this;
    }
    
    
    
}
