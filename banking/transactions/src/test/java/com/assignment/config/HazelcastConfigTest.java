package com.assignment.config;

import com.hazelcast.config.Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HazelcastConfigTest {
    @InjectMocks
    private HazelcastConfig hazelcastConfig;

    @Test
    void hazelConfig() {
       Config result= hazelcastConfig.hazelConfig();
       assertNotNull(result);
    }
}