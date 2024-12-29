package com.example.jsp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
    private String id;
    private String name;
    private String password;
    private String email;
    private String address;
}
