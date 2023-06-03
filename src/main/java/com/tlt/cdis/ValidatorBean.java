/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.tlt.cdis;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Lenovo
 */
@Named(value = "validatorBean")
@SessionScoped
public class ValidatorBean implements Serializable {

    public ValidatorBean() {
    }
    public void validateGoPerson(FacesContext fc, UIComponent uic, Object obj){
        Integer person = (Integer) obj;
        if(person <= 0){
            FacesMessage msg = new FacesMessage("Person cannot be 0");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
