package com.codigo.infraestructure.rest;

import com.codigo.infraestructure.response.ReniecResponse;
import com.codigo.infraestructure.response.ReniecResponseA;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reniecClient", url = "${reniec.base-url}")
public interface ReniecClient {

    @GetMapping("/dni")
    ReniecResponseA getInforReniec(@RequestParam("numero") String numero,
                                   @RequestHeader("Authorization") String auth);
}
