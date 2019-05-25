package com.memorynotfound.springboot;

import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.TopicConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelCastConfig(){
    	TopicConfig topic = new TopicConfig("test").addMessageListenerConfig(new ListenerConfig("com.memorynotfound.springboot.ExampleMessageListener"));
        return new Config()
                .setInstanceName("hazelcast-instance")
                .addListenerConfig(new ListenerConfig("com.memorynotfound.springboot.ExampleDistObjListener" ) )
                .addTopicConfig(topic)
                .addMapConfig(
                        new MapConfig()
                                .setName("instruments")
                                .addEntryListenerConfig(new EntryListenerConfig("com.memorynotfound.springboot.ExampleEntryListener",true,true))
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                //.setTimeToLiveSeconds(20)
                                
                		);
    }

}