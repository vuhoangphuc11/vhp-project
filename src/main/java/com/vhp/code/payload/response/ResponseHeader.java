package com.vhp.code.payload.response;

import com.vhp.code.util.DateUtil;
import lombok.Data;

@Data
public class ResponseHeader {

    private String msgDate = DateUtil.getCurrentDate();
    private int status;

    public ResponseHeader(int status) {
        this.status = status;
    }
}
