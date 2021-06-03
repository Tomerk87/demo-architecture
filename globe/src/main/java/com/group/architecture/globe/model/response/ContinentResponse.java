package com.group.architecture.globe.model.response;

import com.group.architecture.globe.model.entity.Continent;
import com.group.architecture.globe.model.entity.Country;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("Continent")
public class ContinentResponse implements Serializable {

    @Id
    private long id;

    private String name;

    private Set<CountryResponse> countries = new HashSet<>();

    public ContinentResponse(Continent continent) {
        this.id = continent.getId();
        this.name = continent.getName();
        if (!countries.isEmpty()) {
            for (Country c : continent.getCountries()) countries.add(new CountryResponse(c));
        }
    }

}
