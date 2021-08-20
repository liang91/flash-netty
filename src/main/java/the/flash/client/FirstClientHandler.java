package the.flash.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    private byte[] bytes = "are you ok\n".getBytes();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.executor().scheduleAtFixedRate(() -> {
            ByteBuf byteBuf = ctx.alloc().ioBuffer();
            byteBuf.writeBytes(bytes);
            ctx.channel().writeAndFlush(byteBuf);
        }, 0, 1, TimeUnit.MILLISECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf reqBuf = (ByteBuf) msg;
        System.out.println("客户端读到数据 -> " + reqBuf.toString(StandardCharsets.UTF_8));
        reqBuf.release();
    }
}
