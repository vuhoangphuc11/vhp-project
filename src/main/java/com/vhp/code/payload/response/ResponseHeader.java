package com.vhp.code.payload.response;

import lombok.Data;

@Data
public class ResponseHeader {
    private int status;

    public ResponseHeader(int status) {
        this.status = status;
    }
}
