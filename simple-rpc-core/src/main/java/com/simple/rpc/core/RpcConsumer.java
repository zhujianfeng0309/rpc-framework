package com.simple.rpc.core;


import com.simple.rpc.core.handler.RpcResponseHandler;
import com.simple.rpc.protocol.codec.SimpleRpcDecoder;
import com.simple.rpc.protocol.codec.SimpleRpcEncoder;
import com.simple.rpc.common.RpcServiceHelper;
import com.simple.rpc.common.SimpleRpcRequest;
import com.simple.rpc.protocol.SimpleRpcProtocol;
import com.simple.rpc.common.ServiceMeta;
import com.simple.rpc.registry.RegistryService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcConsumer {
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;

    public RpcConsumer() {
        bootstrap = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup(4);
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new SimpleRpcEncoder())
                                .addLast(new SimpleRpcDecoder())
                                .addLast(new RpcResponseHandler());
                    }
                });
    }

    public void sendRequest(SimpleRpcProtocol<SimpleRpcRequest> protocol, RegistryService registryService) throws Exception {
        SimpleRpcRequest request = protocol.getBody();
        Object[] params = request.getParams();
        String serviceKey = RpcServiceHelper.buildServiceKey(RpcServiceHelper.buildServiceName(request.getAppId(),request.getClassName()), request.getServiceVersion());
        //如果是2个节点，通过参数的hashcode 实现负载均衡
        int invokerHashCode = params.length > 0 ? params[0].hashCode() : serviceKey.hashCode();
        ServiceMeta serviceMetadata = registryService.discovery(serviceKey, invokerHashCode);

        if (serviceMetadata != null) {
            ChannelFuture future = bootstrap.connect(serviceMetadata.getServiceAddr(), serviceMetadata.getServicePort()).sync();
            future.addListener((ChannelFutureListener) arg0 -> {
                if (future.isSuccess()) {
                    log.info("connect rpc server {} on port {} success.", serviceMetadata.getServiceAddr(), serviceMetadata.getServicePort());
                } else {
                    log.error("connect rpc server {} on port {} failed.", serviceMetadata.getServiceAddr(), serviceMetadata.getServicePort());
                    future.cause().printStackTrace();
                    eventLoopGroup.shutdownGracefully();
                }
            });
            future.channel().writeAndFlush(protocol);
        }
    }
}