package com.hieupahm.payload.dto;

import java.util.Map;
import lombok.Data;

@Data
public class KeycloakRoleDTO {
    private String id;
    private String name;
    private String description;
    private boolean composite;
    private boolean clientRole;
    private String containerId;
    private Map<String, Object> attributes;
}
