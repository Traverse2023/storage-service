package com.traverse.storage.models;

import lombok.Getter;
import lombok.Setter;

/**
 * @author isfar
 *
 * This is a model class for User
 */
@Getter
@Setter
public class User extends Neo4jNode {

    private String id;

    private String firstname;

    private String lastName;

    private String pfpUrl;

    private String username;

    public User() {
        this.id = this.getElementId();
        this.firstname = (String) this.getProperties().get("firstName");
        this.lastName = (String) this.getProperties().get("lastName");
        this.pfpUrl = (String) this.getProperties().get("pfpUrl");
        this.username = (String) this.getProperties().get("username");
    }
}

