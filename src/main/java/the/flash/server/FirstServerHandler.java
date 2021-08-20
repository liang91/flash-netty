package the.flash.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    private final byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》\n".getBytes(StandardCharsets.UTF_8);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf reqBuf = (ByteBuf) msg;
        System.out.println("服务端收到：" + reqBuf.toString(StandardCharsets.UTF_8));

        ByteBuf out = genResp(ctx);
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf genResp(ChannelHandlerContext ctx) {
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes(bytes);
        return buf;
    }
}
