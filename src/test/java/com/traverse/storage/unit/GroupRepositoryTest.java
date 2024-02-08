package com.traverse.storage.unit;

import com.traverse.storage.group.GroupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DataMongoTest
public class GroupRepositoryTest {

    @Autowired
    GroupRepository groupRepository;

    @Test
    public void findGroupMessagesReturnsGroupMessages() {

    }
}
