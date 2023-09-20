package com.simple.rpc.core.handler;



import com.simple.rpc.core.SimpleRpcFuture;
import com.simple.rpc.core.request.SimpleRpcRequestHolder;
import com.simple.rpc.protocol.SimpleRpcProtocol;
import com.simple.rpc.common.SimpleRpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcResponseHandler extends SimpleChannelInboundHandler<SimpleRpcProtocol<SimpleRpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SimpleRpcProtocol<SimpleRpcResponse> msg) {
        long requestId = msg.getHeader().getRequestId();
        SimpleRpcFuture<SimpleRpcResponse> future = SimpleRpcRequestHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getBody());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}