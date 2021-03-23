package com.bda.pineapplecds;

// import com.google.protobuf.CodedOutputStream;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class PSIMKafkaSerialiser implements Serializer<byte[]>
{

    @Override
    public byte[] serialize(String topic, byte[] data)
    {
        // ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        // CodedOutputStream outputStream = CodedOutputStream.newInstance(byteStream);
        return data;
    }
}
