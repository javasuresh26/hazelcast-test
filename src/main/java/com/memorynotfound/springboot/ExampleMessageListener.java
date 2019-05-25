package com.memorynotfound.springboot;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class ExampleMessageListener implements MessageListener<String> {

    public void onMessage( Message<String> message ) {
    	String myEvent = message.getMessageObject();
        System.out.println( "Message received = " + myEvent.toString() );
    }
}