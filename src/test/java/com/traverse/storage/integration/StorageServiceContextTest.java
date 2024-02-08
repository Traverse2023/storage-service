package com.traverse.storage.integration;

import com.traverse.storage.group.GroupController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StorageServiceContextTest {

    @Autowired
    private GroupController groupController;

}
