package com.group.architecture.gateway.feign;

import com.group.architecture.gateway.model.GlobeContinent;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient(value = "Globe", url = "${globe.base.url}")
public interface GlobeFeignClient {


    @GetMapping(value = "${globe.continent.url}/all")
    List<GlobeContinent> getAllContinents();

    @GetMapping(value = "${globe.continent.url}/{id}")
    ResponseEntity<GlobeContinent> getContinentById(@PathVariable("id") long id, @RequestHeader Map<String, String> headers);
}
