package com.simple.rpc.core.proxy;


import com.simple.rpc.common.SimpleRpcRequest;
import com.simple.rpc.common.SimpleRpcResponse;
import com.simple.rpc.core.RpcConsumer;
import com.simple.rpc.core.SimpleRpcFuture;
import com.simple.rpc.core.request.SimpleRpcRequestHolder;
import com.simple.rpc.protocol.msg.MsgHeader;
import com.simple.rpc.protocol.msg.MsgType;
import com.simple.rpc.protocol.ProtocolConstants;
import com.simple.rpc.protocol.SimpleRpcProtocol;
import com.simple.rpc.protocol.serialization.SerializationTypeEnum;
import com.simple.rpc.registry.RegistryService;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class RpcInvokerProxy implements InvocationHandler {

    private final String appId;
    private final String serviceVersion;
    private final long timeout;
    private final RegistryService registryService;

    public RpcInvokerProxy(String appId ,String serviceVersion, long timeout, RegistryService registryService) {
        this.serviceVersion = serviceVersion;
        this.timeout = timeout;
        this.registryService = registryService;
        this.appId = appId;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        SimpleRpcProtocol<SimpleRpcRequest> protocol = new SimpleRpcProtocol<>();
        MsgHeader header = new MsgHeader();
        long requestId = SimpleRpcRequestHolder.REQUEST_ID_GEN.incrementAndGet();
        header.setMagic(ProtocolConstants.MAGIC);
        header.setVersion(ProtocolConstants.VERSION);
        header.setRequestId(requestId);
        header.setSerialization((byte) SerializationTypeEnum.HESSIAN.getType());
        header.setMsgType((byte) MsgType.REQUEST.getType());
        header.setStatus((byte) 0x1);
        protocol.setHeader(header);

        SimpleRpcRequest request = new SimpleRpcRequest();
        request.setServiceVersion(this.serviceVersion);
        request.setClassName(method.getDeclaringClass().getName());
        request.setAppId(appId);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        protocol.setBody(request);

        RpcConsumer rpcConsumer = new RpcConsumer();
        SimpleRpcFuture<SimpleRpcResponse> future = new SimpleRpcFuture<>(new DefaultPromise<>(new DefaultEventLoop()), timeout);
        SimpleRpcRequestHolder.REQUEST_MAP.put(requestId, future);
        rpcConsumer.sendRequest(protocol, this.registryService);

        // TODO hold request by ThreadLocal


        return future.getPromise().get(future.getTimeout(), TimeUnit.MILLISECONDS).getData();
    }
}