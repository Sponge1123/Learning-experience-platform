package com.buka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userName;
    private String nickName;
    private String password;
    private String email;
    private String sex;
    private String phonenumber;
    private List<String> roleIds;
}
