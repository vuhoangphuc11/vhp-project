package com.vhp.code.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<B> {

    @Valid
    private ResponseHeader header;
    @Valid
    private B body;
}
