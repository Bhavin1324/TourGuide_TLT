package com.tlt.converters;

import com.tlt.ejb.AdminLocal;
import com.tlt.entities.GuideMaster;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;

/**
 *
 * @author Lenovo
 */
@FacesConverter("conv_select_guide")
public class GuideMasterConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            InitialContext ic = new InitialContext();
            AdminLocal adminLogic = (AdminLocal) ic.lookup("java:module/Admin");
            if (string != null && !string.isEmpty()) {
                return adminLogic.getGuideById(string);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object instanceof GuideMaster) {
            return String.valueOf(((GuideMaster) object).getId());
        }
        return null;
    }

}
