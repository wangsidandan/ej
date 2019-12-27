package com.briup.common.domain.basic;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class BaseUser implements Serializable{
    
	@Override
	public String toString() {
		return "BaseUser [id=" + id + ", username=" + username + ", password=" + password + ", telephone=" + telephone
				+ ", realname=" + realname + ", gender=" + gender + ", birth=" + birth + ", registerTime="
				+ registerTime + ", status=" + status + ", userFace=" + userFace + "]";
	}

	private static final long serialVersionUID = 1L;

	private Long id;

    private String username;

    private String password;

    private String telephone;

    private String realname;

    private String gender;

    private Long birth;

    private Long registerTime;

    private String status;

    private String userFace;
    
    

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getBirth() {
        return birth;
    }

    public void setBirth(Long birth) {
        this.birth = birth;
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

	
}