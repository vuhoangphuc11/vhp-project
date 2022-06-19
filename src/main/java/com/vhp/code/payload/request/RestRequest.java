package com.vhp.code.payload.request;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestRequest<B> {

  @Valid
  private RequestHeader header;
  @Valid
  private B body;
}
