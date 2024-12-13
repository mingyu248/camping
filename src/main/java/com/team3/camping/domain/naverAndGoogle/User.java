package com.team3.camping.domain.naverAndGoogle;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Getter
@Setter // 추가
@ToString // 추가
@NoArgsConstructor
@Entity
@SequenceGenerator(
	    name           = "USER_SEQ_GENERATOR",
	    sequenceName   = "USER_SEQ",
	    initialValue   = 1,
	    allocationSize = 1)
@Table(name="user_tbl")
public class User extends BaseTimeEntity {

    @Id
    @Column(nullable=false, precision=10, scale=0) // number(10,0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    			   generator = "USER_SEQ_GENERATOR")
    private BigDecimal id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    // 인증 포털 이름 : 전송용으로만 사용 : 추가  ex) Naver, Google
    @Column(name="auth_vendor")
    private String authVendor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder // String authVendor 추가
    public User(String name, String email, String picture, Role role, String authVendor) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.authVendor = authVendor; // 추가
    }

    public User update(String name, String picture, String authVendor) { // authVendor 추가
        this.name = name;
        this.picture = picture;
        this.authVendor = authVendor; // 추가

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}