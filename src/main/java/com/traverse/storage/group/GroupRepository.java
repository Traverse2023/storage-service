package com.traverse.storage.group;

import com.traverse.storage.models.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
}
