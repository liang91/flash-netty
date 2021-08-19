package the.flash.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        for (int i = 0; i < 10000000; i++) {
            ctx.channel().writeAndFlush(genReqBuf(ctx));
            if (i % 1000000 == 0) {
                System.out.println(i);
            }
        }
    }

    private ByteBuf genReqBuf(ChannelHandlerContext ctx) {
        String msg = "are you ok "+ ++count +"\n";
        byte[] bytes = msg.getBytes();
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf reqBuf = (ByteBuf) msg;
        System.out.println("客户端读到数据 -> " + reqBuf.toString(StandardCharsets.UTF_8));
        reqBuf.release();
    }
}
