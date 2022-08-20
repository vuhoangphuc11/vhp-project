package com.vhp.code.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestRequest<B> {

    @Valid
    private RequestHeader header;
    @Valid
    private B body;
}
