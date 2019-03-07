package cn.shenyanchao.pomelo.rpc.serialize.proto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.Message;

import cn.shenyanchao.pomelo.rpc.serialize.Serialization;

/**
 * @author shenyanchao
 * @since 2019-03-06 21:17
 */
public class ProtoBufSerialization implements Serialization {

    private static Map<String, Message> messages = new ConcurrentHashMap<>();

    public static void addMessage(String className, Message message) {
        messages.putIfAbsent(className, message);
    }

    @Override
    public byte[] serialize(Object object) throws Exception {
        if (!(object instanceof Message)) {
            throw new Exception(
                    "Send object is not type of com.google.protobuf.Message,please sure the object is generated by "
                            + "pb,object is: "
                            + object);
        }
        Message message = (Message) object;
        return message.toByteArray();
    }

    @Override
    public Object deserialize(String className, byte[] bytes) throws Exception {
        Message message = messages.get(className);
        return message.newBuilderForType().mergeFrom(bytes).build();
    }
}
