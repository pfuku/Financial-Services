package org.finance.infra.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "FNC_CUSTOMER_USER", schema = "FNC")
@Getter
@Setter
public class UserEntity implements Serializable {
    @Id
    private Long Id;

    @Column(name = "USERNAME", length = 50)
    private String userName;

    @Column(name = "USEREMAIL", length = 50)
    private String userEmailAddress;

    @Column(name = "PASSWORD", length = 50)
    private String userPassword;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "SURNAME", length = 50)
    private String surName;

    @Column(name = "ADDRESS", length = 200)
    private String address;

    @Column(name = "CREATEDATE")
    private Long createDate;

    @Column(name = "CREATETIME")
    private Long createTime;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "STATE")
    private Integer state;
}
