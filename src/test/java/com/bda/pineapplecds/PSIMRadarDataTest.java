package com.bda.pineapplecds;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.connectors.InMemorySink;
import io.smallrye.reactive.messaging.connectors.InMemorySource;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
public class PSIMRadarDataTest
{
    @Inject
    @Any
    InMemoryConnector connector;

    @Test
    public void testHelloEndpoint() 
    {
        InMemorySource<byte[]> radarIn = connector.source("radar");
        InMemorySink<byte[]> radarOut = connector.sink("radarIn");

        try
        {
            String strMessage = "0123456789some radar drivel9876543210";
            DatagramSocket socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName("239.192.0.101");
            byte[] msg = strMessage.getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length,
                group, 10001);
            socket.send(packet);
            socket.close();
            assertEquals(radarIn.name(), "radar");
            await().<List<? extends Message<byte[]>>>until(radarOut::received, t -> t.size() == 1);
        }
        catch(IOException ex)
        {
            assertTrue(false);
        }
    }

}