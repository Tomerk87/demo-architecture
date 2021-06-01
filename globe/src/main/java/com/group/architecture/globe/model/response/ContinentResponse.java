package com.group.architecture.globe.model.response;

import com.group.architecture.globe.model.entity.Continent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("Continent")
public class ContinentResponse implements Serializable {

    @Id
    private long id;

    private String name;

    public ContinentResponse(Continent continent) {
        this.id = continent.getId();
        this.name = continent.getName();
    }

}
