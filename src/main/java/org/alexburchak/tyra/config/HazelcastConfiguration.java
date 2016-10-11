package org.alexburchak.tyra.config;

import com.hazelcast.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author alexburchak
 */
@Configuration
public class HazelcastConfiguration {
    @Bean(destroyMethod = "shutdown")
    public HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance();
    }

    @Bean
    public IExecutorService executorService() {
        return hazelcastInstance().getExecutorService("executorService");
    }

    @Bean
    public IMap<String, Member> membersMap() {
        return hazelcastInstance().getMap("membersMap");
    }

    @Bean
    public Member member() {
        return hazelcastInstance().getCluster().getLocalMember();
    }
}
