package com.example.ticketing.domain.airpalne;


import com.example.ticketing.core.domain.BaseEntity;
import com.example.ticketing.domain.transactionLog.TransactionLog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tb_airplane")
public class Airplane extends BaseEntity {

    @Id
    @Column(name = "ap_num", nullable = false)
    private int apNum;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "start_port", length = 50, nullable = false)
    private String startPort;

    @Column(name = "end_port", length = 50, nullable = false)
    private String endPort;

    @OneToOne(mappedBy = "airplane", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private TransactionLog transactionLog;

}
