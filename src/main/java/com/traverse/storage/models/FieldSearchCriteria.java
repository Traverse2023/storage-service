package com.traverse.storage.models;

import com.traverse.storage.enums.SearchMode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author isfar
 *
 * FieldSearchCriteria Model class
 * @param <T>
 */
public class FieldSearchCriteria<T> {

    /**
     * searchMode is either IN for more static values while RANGE is for values like dates
     */
    @Getter
    @Setter
    private SearchMode searchMode;

    /**
     * name of the field in the item of the collection, ex, user model has firstName
     */
    @Getter
    @Setter
    private String field;

    /**
     * array of values for the field, ex, ["isfar", "bryan"]
     */
    @Getter
    @Setter
    private List<T> values;

    public FieldSearchCriteria(){

    }


}