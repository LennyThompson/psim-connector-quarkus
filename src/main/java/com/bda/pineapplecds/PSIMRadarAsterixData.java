package com.bda.pineapplecds;

import javax.enterprise.context.ApplicationScoped;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class PSIMRadarAsterixData extends PSIMDataSource
{
    @Outgoing("asterix-radar-in")
    @Blocking("udp-handler-pool")
    public Multi<byte[]> generate()
    {
        m_nUDPPort = 30002;
        m_strGroupIP = "239.192.0.101";
        return Multi.createFrom().emitter
            (
                emitter ->
                {
                    this.emitDataSource(emitter);
                }
            );
    }

}