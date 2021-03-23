package com.bda.pineapplecds;

// import com.google.protobuf.CodedInputStream;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class PSIMKafkaDeserialiser implements Deserializer<byte[]>
{

    @Override
    public byte[] deserialize(String topic, byte[] data)
    {
        try
        {
            return data;
        } 
        catch (Exception e)
        {
            //ToDo
        }

        return null;
    }
}