package com.group.architecture.gateway.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {

    private String continentId;

    private String eTag;
}
