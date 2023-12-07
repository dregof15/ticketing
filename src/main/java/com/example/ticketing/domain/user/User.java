package com.example.ticketing.domain.user;

import com.example.ticketing.core.domain.BaseEntity;
import com.example.ticketing.domain.transactionLog.TransactionLog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "tb_member")
public class User extends BaseEntity {

    @Id
    @Column(name = "id", length = 20, nullable = false)
    private String id;

    @Column(name = "pw", length = 20, nullable = false)
    private String pw;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "vip_dp", length = 50)
    private String vipDp;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private TransactionLog transactionLog;

}
