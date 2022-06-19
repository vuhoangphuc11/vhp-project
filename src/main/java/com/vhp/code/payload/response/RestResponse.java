package com.vhp.code.payload.response;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<B> {

  @Valid
  private ResponseHeader header;
  @Valid
  private B body;
}
