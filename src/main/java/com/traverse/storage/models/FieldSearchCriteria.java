package com.traverse.storage.models;

import com.traverse.storage.enums.SearchMode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author isfar
 *
 * FieldSearchCriteria Model class
 * @param <T>
 */
@Getter
@Setter
@NoArgsConstructor
public class FieldSearchCriteria<T> {

    /**
     * searchMode is either IN for more static values while RANGE is for values like dates
     */
    private SearchMode searchMode;

    /**
     * name of the field in the item of the collection, ex, user model has firstName
     */
    private String field;

    /**
     * array of values for the field, ex, ["isfar", "bryan"]
     */
    private List<T> values;

}