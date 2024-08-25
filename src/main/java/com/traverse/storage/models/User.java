package com.traverse.storage.models;

import lombok.Getter;
import lombok.Setter;

/**
 * @author isfar
 *
 * This is a model class for User
 */
public class User extends Neo4jNode {


    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String firstname;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String pfpUrl;

    @Getter
    @Setter
    private String username;

    public User() {
        this.id = this.getElementId();
        this.firstname = (String) this.getProperties().get("firstName");
        this.lastName = (String) this.getProperties().get("lastName");
        this.pfpUrl = (String) this.getProperties().get("pfpUrl");
        this.username = (String) this.getProperties().get("username");
    }
}

