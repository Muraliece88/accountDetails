package com.assignment.config;

import com.assignment.entities.Customers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class HazelcastConfig {


    @Bean
    public Config hazelConfig()
    {
        Config config=new Config();
        config.setInstanceName("hazel-instance")
                .addMapConfig(new MapConfig().setName("customers"))
                .addMapConfig(new MapConfig().setName("account"))
                .addMapConfig(new MapConfig().setName("transactions"));
        NetworkConfig networkConfig=config.getNetworkConfig();
        JoinConfig joinConfig=networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(true);
        joinConfig.getTcpIpConfig().addMember("127.0.0.1:8081").addMember("127.0.0.1:8082");
        return config;
    }


}
