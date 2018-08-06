package com.tihom.seller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 缓存的话直接使用map
 * @author TiHom
 * create at 2018/8/4 0004.
 */
//@Component
public class HazelcastMapTest {

//    @Autowired
//    private HazelcastInstance hazelcastInstance;
//
//    @PostConstruct
//    public void put(){
//        Map map = hazelcastInstance.getMap("tihom");
//        map.put("name","tihom");
//    }

}
