package com.example.ticketing.core.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class) /* 생성 수정 삭제시 실행되며 그 당시 생성 수정 일자를 자동으로 설정하는 역할을 함. */
public class BaseEntity {

    @CreatedBy
    @Column(name = "regist_id", updatable = false)
    private String registId;

    @CreatedDate
    @Column(name = "regist_dt", updatable = false)
    private LocalDateTime registDt;

    @LastModifiedBy
    @Column(name = "updt_id")
    private String updtId;

    @LastModifiedDate
    @Column(name = "updt_dt")
    private LocalDateTime updtDt;
    public BaseEntity (String id) {
        this.registId = id;
        this.registDt = LocalDateTime.now();
        this.updtId = id;
        this.updtDt = LocalDateTime.now();
    }
}
