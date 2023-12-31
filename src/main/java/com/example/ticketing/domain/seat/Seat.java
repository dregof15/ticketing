package com.example.ticketing.domain.seat;

import com.example.ticketing.core.domain.BaseEntity;
import com.example.ticketing.domain.transactionLog.TransactionLog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tb_seat")
public class Seat extends BaseEntity {

    @Id
    @Column(name = "num",nullable = false)
    private int stNum;

    @Column(name = "rating",nullable = false)
    private int rating;

    @OneToOne(mappedBy = "seat", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private TransactionLog seat;

}
