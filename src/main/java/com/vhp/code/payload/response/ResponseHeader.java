package com.vhp.code.payload.response;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
