package com.liang;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        System.out.println("初始化Channel");
                    }
                });
        connect(bootstrap, "www.baidu.com", 80);
    }

    private static void connect(Bootstrap bootstrap, String host, int port) {
        ChannelFuture future = bootstrap.connect(host, port);
        future.addListener(f -> {
            if (f.isSuccess()) {
                System.out.println("连接成功");
            } else {
                System.out.println("连接失败");
            }
        });
        // 断开连接后重连
        future.channel().closeFuture().addListener(f -> {
            if (f.isDone()) {
                System.out.println("连接关闭，开始重连");
                Thread.sleep(15000);
                connect(bootstrap, host, port);
            }
        });
    }
}
