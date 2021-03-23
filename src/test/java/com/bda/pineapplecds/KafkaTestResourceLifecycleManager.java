package com.bda.pineapplecds;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;

import java.util.HashMap;
import java.util.Map;

public class KafkaTestResourceLifecycleManager  implements QuarkusTestResourceLifecycleManager
{
    @Override
    public Map<String, String> start()
    {
        Map<String, String> env = new HashMap<>();
        Map<String, String> propsIn = InMemoryConnector.switchIncomingChannelsToInMemory("radarIn");
        Map<String, String> propsOut = InMemoryConnector.switchOutgoingChannelsToInMemory("radar");
        env.putAll(propsIn);
        env.putAll(propsOut);
        return env;
    }

    @Override
    public void stop()
    {
        InMemoryConnector.clear();
    }
}
