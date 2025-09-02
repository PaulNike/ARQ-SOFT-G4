package com.codigo.client;

import com.codigo.dto.ReniecResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reniecClient", url = "${reniec.base-url}")
public interface ReniecClient {

    @GetMapping("/dni")
    ReniecResponse getInfoReniec(@RequestParam("numero") String numero,
                                 @RequestHeader("Authorization") String auth);
}
