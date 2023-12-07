package com.example.ticketing.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
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

}
