package rpc.transport;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import rpc.Constants;
import rpc.protocol.Message;
import rpc.protocol.Response;

public class DemoRpcClientHandler extends SimpleChannelInboundHandler<Message<Response>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message<Response> message) throws Exception {
        NettyResponseFuture responseFuture =
                Connection.IN_FLIGHT_REQUEST_MAP.remove(message.getHeader().getMessageId());
        Response response = message.getContent();
        // 心跳消息特殊处理
        if (response == null && Constants.isHeartBeat(message.getHeader().getExtraInfo())) {
            response = new Response();
            response.setCode(Constants.HEARTBEAT_CODE);
        }
        responseFuture.getPromise().setSuccess(response.getResult());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client and server connected ");
        super.channelActive(ctx);
    }
}