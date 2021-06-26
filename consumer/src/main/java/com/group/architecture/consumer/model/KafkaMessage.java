package com.group.architecture.consumer.model;

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
