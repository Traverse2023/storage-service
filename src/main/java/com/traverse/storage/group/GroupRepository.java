package com.traverse.storage.group;

import com.traverse.storage.models.Group;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, String> {

    @Query(value = "{ '_id' : ?0}", fields = "{ 'channels.?1.messages': { '$slice': [ ?2, ?3 ]}}")
    List<Group> findGroupMessages(String groupId, String channelName, int messagesSkipped, int pageSize);

}
