package com.hieupahm.payload.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IpnResponse {
    @JsonProperty("Rspcode")
    private String responseCode;
    @JsonProperty("Message")
    private String message;
}
