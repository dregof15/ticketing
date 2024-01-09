package com.example.ticketing.domain.user;

import com.example.ticketing.core.domain.BaseEntity;
import com.example.ticketing.domain.transactionLog.TransactionLog;
import com.example.ticketing.web.dto.user.JoinUserDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_member")
public class User extends BaseEntity {

    @Id
    @Column(name = "id", length = 20, nullable = false)
    private String id;

    @Column(name = "pw", length = 20, nullable = false)
    private String pw;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "zip", length = 50)
    private String zip;

    @Column(name = "adress", length = 50)
    private String adress;

    @Column(name = "adress_detail", length = 50)
    private String adressDetail;

    @Column(name = "sido", length = 50)
    private String sido;

    @Column(name = "sigungu", length = 50)
    private String sigungu;

    @Column(name = "vip_dp", length = 50)
    private String vipDp;

    @Column(name = "used_yn", length = 1)
    private String usedYn;

    @Column(name = "del_yn", length = 1)
    private String delYn;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private TransactionLog transactionLog;

    private User (String id) {
        super(id);
    }

    public static User save(JoinUserDto joinUserDto) {
        User user = new User(joinUserDto.getId());
        user.id = joinUserDto.getId();
        user.pw = joinUserDto.getPw();
        user.name = joinUserDto.getName();
        user.phone = joinUserDto.getPhone();
        user.email = joinUserDto.getIdEmail() + joinUserDto.getDomainEmail();
        user.zip = joinUserDto.getZip();
        user.adress = joinUserDto.getAdress();
        user.adressDetail = joinUserDto.getAdressDetail();
        user.sido = joinUserDto.getSido();
        user.sigungu = joinUserDto.getSigungu();
        user.delYn = "N";
        user.usedYn = "Y";
        user.vipDp = "0";
        return user;
    }
}
