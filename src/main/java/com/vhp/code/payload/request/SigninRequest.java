package com.vhp.code.payload.request;

import lombok.Data;

@Data
public class SigninRequest {

    private String username;
    private String password;
}
