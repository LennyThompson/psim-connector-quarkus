package com.bda.pineapplecds;

import io.smallrye.mutiny.subscription.MultiEmitter;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class PSIMDataSource
{
    private static final Logger LOGGER = Logger.getLogger("PSIMDataSource");
    protected int m_nUDPPort = 1;
    protected String m_strGroupIP = "224.0.0.101";

    public void emitDataSource(MultiEmitter<? super byte[]> emitter)
    {
        try (MulticastSocket socket = new MulticastSocket(m_nUDPPort))
        {
            InetAddress group =
                InetAddress.getByName(m_strGroupIP);
            LOGGER.infov("Multicast Receiver running on:{0}",
                socket.getLocalSocketAddress());
            socket.joinGroup(group);
            LOGGER.info("PSIMConnector joined multicast group");

            byte[] buffer = new byte[1024 * 16];
            DatagramPacket packet =
                new DatagramPacket(buffer, buffer.length);

            while (true)
            {
                socket.receive(packet);
                byte[] data = Arrays.copyOf(packet.getData(), packet.getLength());

                String received = new String(data);
                LOGGER.debug(received.trim());
                emitter.emit(data);
                Thread.sleep(1);
            }
        }
        catch (IOException ex)
        {
            LOGGER.error("Multicast error...", ex);
            emitter.fail(ex);
        }
        catch(InterruptedException ex)
        {
            LOGGER.error("Thread stopped", ex);
            emitter.fail(ex);
        }
    }

}
