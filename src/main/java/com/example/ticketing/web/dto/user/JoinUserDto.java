package com.example.ticketing.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinUserDto {

    private String id;

    private String pw;

    private String name;

    private String phone;

    private String idEmail;

    private String domainEmail;

    private String zip;

    private String adress;

    private String adressDetail;

    private String sido;

    private String sigungu;
}
