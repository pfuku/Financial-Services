package org.finance.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

@Getter
@Setter
@AllArgsConstructor
public class GenericRequest {
    private String command;
    private String payload;
}
