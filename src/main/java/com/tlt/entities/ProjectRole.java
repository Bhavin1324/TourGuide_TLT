/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlt.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author kunal
 */
@Entity
@Table(name = "project_role")
@NamedQueries({
    @NamedQuery(name = "ProjectRole.findAll", query = "SELECT p FROM ProjectRole p"),
    @NamedQuery(name = "ProjectRole.findByRole", query = "SELECT p FROM ProjectRole p WHERE p.projectRolePK.role = :role"),
    @NamedQuery(name = "ProjectRole.findByUsername", query = "SELECT p FROM ProjectRole p WHERE p.projectRolePK.username = :username")})
public class ProjectRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProjectRolePK projectRolePK;
    @JoinColumn(name = "Username", referencedColumnName = "Username", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserMaster userMaster;

    public ProjectRole() {
    }

    public ProjectRole(ProjectRolePK projectRolePK) {
        this.projectRolePK = projectRolePK;
    }

    public ProjectRole(String role, String username) {
        this.projectRolePK = new ProjectRolePK(role, username);
    }

    public ProjectRolePK getProjectRolePK() {
        return projectRolePK;
    }

    public void setProjectRolePK(ProjectRolePK projectRolePK) {
        this.projectRolePK = projectRolePK;
    }

    public UserMaster getUserMaster() {
        return userMaster;
    }

    public void setUserMaster(UserMaster userMaster) {
        this.userMaster = userMaster;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectRolePK != null ? projectRolePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectRole)) {
            return false;
        }
        ProjectRole other = (ProjectRole) object;
        if ((this.projectRolePK == null && other.projectRolePK != null) || (this.projectRolePK != null && !this.projectRolePK.equals(other.projectRolePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tlt.entities.ProjectRole[ projectRolePK=" + projectRolePK + " ]";
    }
    
}
