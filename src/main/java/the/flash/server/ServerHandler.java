package the.flash.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import the.flash.protocol.Packet;
import the.flash.protocol.PacketCodeC;
import the.flash.protocol.request.LoginRequestPacket;
import the.flash.protocol.request.MessageRequestPacket;
import the.flash.protocol.response.LoginResponsePacket;
import the.flash.protocol.response.MessageResponsePacket;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        if (packet instanceof LoginRequestPacket) {
            // 登录流程
            System.out.println(new Date() + ": 收到客户端登录请求……");
            LoginRequestPacket loginReq = (LoginRequestPacket) packet;
            LoginResponsePacket loginRes = new LoginResponsePacket();
            loginRes.setVersion(packet.getVersion());
            if (valid(loginReq)) {
                loginRes.setSuccess(true);
                System.out.println(new Date() + ": 登录成功!");
            } else {
                loginRes.setReason("账号密码校验失败");
                loginRes.setSuccess(false);
                System.out.println(new Date() + ": 登录失败!");
            }
            ByteBuf loginResBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRes);
            ctx.channel().writeAndFlush(loginResBuf);
        } else if (packet instanceof MessageRequestPacket) {
            // 业务消息处理
            MessageRequestPacket msgReq = ((MessageRequestPacket) packet);
            MessageResponsePacket msgRes = new MessageResponsePacket();
            System.out.println("收到客户端消息: " + msgReq.getMessage());
            msgRes.setMessage(msgReq.getMessage());
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), msgRes);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginReq) {
        return !loginReq.getUsername().equals("") && !loginReq.getPassword().equals("");
    }
}
