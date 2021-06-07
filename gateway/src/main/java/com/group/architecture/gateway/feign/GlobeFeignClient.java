package com.group.architecture.gateway.feign;

import com.group.architecture.gateway.model.GlobeContinent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@FeignClient(value = "Globe", url = "${globe.base.url}")
public interface GlobeFeignClient {

    @GetMapping(value = "${globe.continent.url}/all")
    ResponseEntity<List<GlobeContinent>> getAllContinents();

    @PostMapping(value = "${globe.continent.url}/")
    ResponseEntity<GlobeContinent> createContinent(@RequestBody GlobeContinent continent);

    @GetMapping(value = "${globe.continent.url}/{id}")
    ResponseEntity<GlobeContinent> getContinentById(@PathVariable("id") long id, @RequestHeader Map<String, String> headers);

    @PutMapping(value = "${globe.continent.url}/{id}")
    ResponseEntity<GlobeContinent> updateContinentById(@PathVariable("id") long id, @RequestBody GlobeContinent continent, @RequestHeader Map<String, String> headers);

    @DeleteMapping(value = "${globe.continent.url}/{id}")
    ResponseEntity<GlobeContinent> deleteContinentById(@PathVariable("id") long id);
}
