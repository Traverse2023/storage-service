package com.traverse.storage.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author isfar
 *
 * This is a custom Neo4jNode class that is used by the service. It will be a parent to different collection classes
 * (User, Group, etc). The purpose of this class is to solely have a specific type in the controllers that interface
 * with the search endpoints, for example UserController search returns User which is possible because Neo4jService search
 * returns List<Neo4jNode>
 */
public class Neo4jNode {

    @Getter
    @Setter
    private String elementId;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Map<String, Object> properties;
}
