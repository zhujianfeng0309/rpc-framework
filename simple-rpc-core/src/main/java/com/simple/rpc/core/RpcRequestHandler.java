package com.simple.rpc.core;

import com.simple.rpc.protocol.SimpleRpcProtocol;
import com.simple.rpc.protocol.msg.MsgHeader;
import com.simple.rpc.protocol.msg.MsgStatus;
import com.simple.rpc.protocol.msg.MsgType;
import com.simple.rpc.common.SimpleRpcRequest;
import com.simple.rpc.common.SimpleRpcResponse;
import com.simple.rpc.common.RpcServiceHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.reflect.FastClass;

import java.util.Map;

@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<SimpleRpcProtocol<SimpleRpcRequest>> {

    private final Map<String, Object> rpcServiceMap;

    public RpcRequestHandler(Map<String, Object> rpcServiceMap) {
        this.rpcServiceMap = rpcServiceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SimpleRpcProtocol<SimpleRpcRequest> protocol) {
        RpcRequestProcessor.submitRequest(() -> {
            SimpleRpcProtocol<SimpleRpcResponse> resProtocol = new SimpleRpcProtocol<>();
            SimpleRpcResponse response = new SimpleRpcResponse();
            MsgHeader header = protocol.getHeader();
            header.setMsgType((byte) MsgType.RESPONSE.getType());
            try {
                Object result = handle(protocol.getBody());
                response.setData(result);

                header.setStatus((byte) MsgStatus.SUCCESS.getCode());
                resProtocol.setHeader(header);
                resProtocol.setBody(response);
            } catch (Throwable throwable) {
                header.setStatus((byte) MsgStatus.FAIL.getCode());
                response.setMessage(throwable.toString());
                log.error("process request {} error", header.getRequestId(), throwable);
            }
            ctx.writeAndFlush(resProtocol);
        });
    }

    private Object handle(SimpleRpcRequest request) throws Throwable {
        String serviceKey = RpcServiceHelper.buildServiceKey(RpcServiceHelper.buildServiceName(request.getAppId(),request.getClassName()), request.getServiceVersion());
        Object serviceBean = rpcServiceMap.get(serviceKey);

        if (serviceBean == null) {
            throw new RuntimeException(String.format("service not exist: %s:%s", request.getClassName(), request.getMethodName()));
        }

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParams();

        FastClass fastClass = FastClass.create(serviceClass);
        int methodIndex = fastClass.getIndex(methodName, parameterTypes);
        return fastClass.invoke(methodIndex, serviceBean, parameters);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("RpcRequestHandler channelActive ......");
        super.channelActive(ctx);
    }
}