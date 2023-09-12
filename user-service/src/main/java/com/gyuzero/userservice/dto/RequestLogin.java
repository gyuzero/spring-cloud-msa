package com.gyuzero.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLogin {

    private String userId;

    private String password;
}
