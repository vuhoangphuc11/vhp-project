package com.vhp.code.payload.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseHeader {
    private Timestamp msgDate = new Timestamp(System.currentTimeMillis());
    private int status;

    public ResponseHeader(int status) {
        this.status = status;
    }
}
