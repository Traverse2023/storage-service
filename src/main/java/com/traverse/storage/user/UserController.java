package com.traverse.storage.user;
import com.traverse.storage.models.CollectionSearchCriteria;
import com.traverse.storage.models.Neo4jNode;
import com.traverse.storage.models.User;
import com.traverse.storage.neo4j.Neo4jService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author isfar
 *
 * This is the api controller for user
 */
@RestController
@Slf4j
@RequestMapping(path = "api/v1/user")
public class UserController {

    @Autowired
    private Neo4jService<User> neo4JService;

    /**
     *
     */
    @PostMapping("/search")
    public List<Neo4jNode> search(@RequestBody CollectionSearchCriteria criteria) {
        log.info("Invoking Search");
        return neo4JService.search(User.class, criteria);
    }

}
