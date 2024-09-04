package com.traverse.storage.problems;
import com.traverse.storage.models.ProblemsList;
import com.traverse.storage.utils.exceptions.mongo.ProblemNotFoundException;

import java.util.List;

public interface ProblemsRepository {
    ProblemsList getProblemsList(String name,String Id, String level, List<String> tags, String misc, String paginationToken) throws ProblemNotFoundException;
}