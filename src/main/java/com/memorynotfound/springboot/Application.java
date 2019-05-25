package com.memorynotfound.springboot;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

@EnableCaching
@SpringBootApplication
@RestController
public class Application implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    HazelcastInstance hzInstance;
    
    @Autowired
    private MusicService musicService;

    @Autowired
    private CacheManager cacheManager;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Spring Boot Hazelcast Caching Example Configuration");
        log.info("Using cache manager: " + cacheManager.getClass().getName());

//        musicService.clearCache();
//
//        play("trombone");
//        play("guitar");
//        play("trombone");
//        play("bass");
//        play("trombone");
    }

    private void play(String instrument){
        log.info("Calling: " + MusicService.class.getSimpleName() + ".play(\"" + instrument + "\");");
        musicService.play(instrument);
    }
    
    @GetMapping("/getVal")
    public String getVal(@RequestParam String val) {
    	log.info("Calling: " + MusicService.class.getSimpleName() + ".play(\"" + val + "\");");
        return musicService.play(val);
	}
    
    @GetMapping("/clear")
    public String clear() {
    	log.info("clearCache");
    	musicService.clearCache();
    	return "Cleared: " +  LocalDateTime.now();
	}
    
    @GetMapping("/publish")
    public void publish() {
    	log.info("publish");
    	hzInstance.getTopic("test").publish("hello:"+ LocalDateTime.now());
	}
    
}

