package com.simple.rpc.protocol.codec;


import com.simple.rpc.protocol.SimpleRpcProtocol;
import com.simple.rpc.protocol.msg.MsgHeader;
import com.simple.rpc.protocol.msg.MsgType;
import com.simple.rpc.protocol.ProtocolConstants;
import com.simple.rpc.common.SimpleRpcRequest;
import com.simple.rpc.common.SimpleRpcResponse;
import com.simple.rpc.protocol.serialization.RpcSerialization;
import com.simple.rpc.protocol.serialization.SerializationFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;


import java.util.List;

public class SimpleRpcDecoder extends ByteToMessageDecoder {

    /*
    +---------------------------------------------------------------+
    | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
    +---------------------------------------------------------------+
    | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
    +---------------------------------------------------------------+
    |                   数据内容 （长度不定）                          |
    +---------------------------------------------------------------+
    */
    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < ProtocolConstants.HEADER_TOTAL_LEN) {
            return;
        }
        in.markReaderIndex();

        short magic = in.readShort();
        if (magic != ProtocolConstants.MAGIC) {
            throw new IllegalArgumentException("magic number is illegal, " + magic);
        }

        byte version = in.readByte();
        byte serializeType = in.readByte();
        byte msgType = in.readByte();
        byte status = in.readByte();
        long requestId = in.readLong();

        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        MsgType msgTypeEnum = MsgType.findByType(msgType);
        if (msgTypeEnum == null) {
            return;
        }

        MsgHeader header = new MsgHeader();
        header.setMagic(magic);
        header.setVersion(version);
        header.setSerialization(serializeType);
        header.setStatus(status);
        header.setRequestId(requestId);
        header.setMsgType(msgType);
        header.setMsgLen(dataLength);

        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(serializeType);
        switch (msgTypeEnum) {
            case REQUEST:
                SimpleRpcRequest request = rpcSerialization.deserialize(data, SimpleRpcRequest.class);
                if (request != null) {
                    SimpleRpcProtocol<SimpleRpcRequest> protocol = new SimpleRpcProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(request);
                    out.add(protocol);
                }
                break;
            case RESPONSE:
                SimpleRpcResponse response = rpcSerialization.deserialize(data, SimpleRpcResponse.class);
                if (response != null) {
                    SimpleRpcProtocol<SimpleRpcResponse> protocol = new SimpleRpcProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(response);
                    out.add(protocol);
                }
                break;
            case HEARTBEAT:
                // TODO
                break;
        }
    }
}