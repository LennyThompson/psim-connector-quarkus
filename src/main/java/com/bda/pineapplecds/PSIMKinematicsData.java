package com.bda.pineapplecds;

import javax.enterprise.context.ApplicationScoped;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class PSIMKinematicsData extends PSIMDataSource
{
    @Outgoing("kinematics-data-in")
    @Blocking("udp-handler-pool")
    public Multi<byte[]> generate()
    {
        m_nUDPPort = 5555;
        m_strGroupIP = "239.192.0.132";
        return Multi.createFrom().emitter
            (
                emitter ->
                {
                    this.emitDataSource(emitter);
                }
            );
    }

}