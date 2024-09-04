package com.traverse.storage.problems;

import com.traverse.storage.models.Problem;
import com.traverse.storage.models.ProblemsList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/problems")
public class ProblemController {
    private final ProblemsService problemsService;

    @Autowired
    ProblemController(ProblemsService problemsService) {this.problemsService = problemsService;}

    @GetMapping("/search")
    public ProblemsList getProblemsList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String problemId,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String misc,
            @RequestParam(required = false) String paginationToken
    ) {
        return problemsService.getProblemsList(name, problemId, level, tags, misc, paginationToken);
    }


    @PostMapping("/addProblem")
    public Problem createProblem(Problem problem) {
        return problemsService.createProblem(problem);
    }
}