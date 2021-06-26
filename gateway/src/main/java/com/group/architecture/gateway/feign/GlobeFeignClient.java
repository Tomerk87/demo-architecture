package com.group.architecture.gateway.feign;

import com.group.architecture.gateway.model.request.GlobeContinentRequest;
import com.group.architecture.gateway.model.response.GlobeContinentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@FeignClient(value = "Globe", url = "${globe.base.url}")
public interface GlobeFeignClient {

    @GetMapping(value = "${globe.continent.url}/all")
    ResponseEntity<List<GlobeContinentResponse>> getAllContinents();

    @PostMapping(value = "${globe.continent.url}/")
    ResponseEntity<GlobeContinentResponse> createContinent(@RequestBody GlobeContinentRequest continent);

    @GetMapping(value = "${globe.continent.url}/{id}")
    ResponseEntity<GlobeContinentResponse> getContinentById(@PathVariable("id") long id, @RequestHeader Map<String, String> headers);

    @PutMapping(value = "${globe.continent.url}/{id}")
    ResponseEntity<GlobeContinentResponse> updateContinentById(@PathVariable("id") long id, @RequestBody GlobeContinentRequest continent, @RequestHeader Map<String, String> headers);

    @DeleteMapping(value = "${globe.continent.url}/{id}")
    ResponseEntity<GlobeContinentResponse> deleteContinentById(@PathVariable("id") long id);
}
