package com.traverse.storage.models;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author isfar
 *
 * This is the collection search criteria class. This is where devs can pass certain fields to query the collection
 * This is standardized, so should work for all databases, i.e. neo4j or dynamodb.
 *
 */
public class CollectionSearchCriteria {

    /**
     * This field is for text index search. Using user collection as an example,
     * if a user types "Isfar" in search input box, this will search for Isfar
     * in firstName, lastName, pfpURL, and username columns.
     * To create a text index search in a neo4j db, run a command similar to this, which is used for user collection
     * CREATE FULLTEXT INDEX textIndex FOR (n:User) ON EACH [n.firstName, n.lastName, n.pfpUrl, n.username];
     */
    @Getter
    @Setter
    private String text;

    /**
     * This filter toggle allows the search service to know if all your criterias should be appended by either
     * AND or OR. For ex, for problems collection, sometimes you may want to combine criterias like Amazon-tagged
     * question and difficulty (easy). This will narrow your search. It's an AND-based search.
     * However, if for some reason you only want either Amazon-tagged questions or easy questions, this will widen
     * your search. It's an OR-based search. The latter example may not be the best example, but there may be cases
     * where we want an OR-based search.
     */
    @Getter
    @Setter
    private Boolean filter;

    /**
     * This is the field where all field-based criterias are provided.
     */
    @Getter
    @Setter
    private List<FieldSearchCriteria> fieldSearchCriterias;
}