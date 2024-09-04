package com.traverse.storage.problems;
import com.traverse.storage.models.*;
import com.traverse.storage.utils.exceptions.mongo.MessagesNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.traverse.storage.problems.ProblemsDynamoDBRepository.table;


@Slf4j
@Service
@RequiredArgsConstructor
@Builder

public class ProblemsService {
    private ProblemsRepository problemsRepository;
    @Autowired
    public ProblemsService(ProblemsRepository problemsRepository) {this.problemsRepository = problemsRepository;}

    public Problem createProblem(Problem problem) {
        final String sk = String.format("%s#%s", problem.getLevel(), problem.getProblemId());
        Problem newProblem = problem.toBuilder().sk(sk).build();
        table.putItem(problem);
        log.info("Problem created successfully: \n{}", newProblem);
        return newProblem;
    }

    public ProblemsList getProblemsList(String name, String problemId, String level, List<String> tags, String misc, String paginationToken) {
        return problemsRepository.getProblemsList(name, problemId, level, tags, misc, paginationToken);
    }
}