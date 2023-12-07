package com.example.ticketing.domain.transactionLog;


import com.example.ticketing.core.domain.BaseEntity;
import com.example.ticketing.domain.airpalne.Airplane;
import com.example.ticketing.domain.seat.Seat;
import com.example.ticketing.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "tb_transaction_log")
public class TransactionLog extends BaseEntity {

    @Id
    @Column(name = "num",nullable = false)
    private int num;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client")
    private User client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane")
    private Airplane airplane;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat")
    private Seat seat;

}
