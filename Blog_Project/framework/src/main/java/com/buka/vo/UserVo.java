package com.buka.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserVo implements Serializable {
    private Long id;
    private String nickName;
    private String email;
    private String sex;
    private String avatar;
}
