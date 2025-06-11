package com.example.model;

import java.sql.Date;

public class Student {
    private String id;
    private String name;
    private String phone;
    private String email;
    private Date dateOfBirth;
    private String address;
    private String password;
    private Date startYear;
    private Date endYear;
    private Date createAt;
    private Date lastmodified;
    private boolean deleted;
    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getStartYear() {
        return startYear;
    }

    public void setStartYear(Date startYear) {
        this.startYear = startYear;
    }

    public Date getEndYear() {
        return endYear;
    }

    public void setEndYear(Date endYear) {
        this.endYear = endYear;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Student(String id, String name, String phone, String email, Date dateOfBirth, String address, String password, Date startYear, Date endYear) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.password = password;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public Student(String id, String name, String phone, String email, Date dateOfBirth, String address, String password, Date startYear, Date endYear, Date createAt, Date lastmodified, boolean deleted, boolean status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.password = password;
        this.startYear = startYear;
        this.endYear = endYear;
        this.createAt = createAt;
        this.lastmodified = lastmodified;
        this.deleted = deleted;
        this.status = status;
    }

    public Student() {
    }
}
