package com.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // ((ByteBuf) msg).release();

        //
        ByteBuf in = (ByteBuf) msg;
        StringBuilder builder = new StringBuilder();
        // try {
        while (in.isReadable()) {
            // System.out.print((char)in.readByte());
            builder.append((char) in.readByte());
            // System.out.flush();
        }

        // } finally {
        // ReferenceCountUtil.release(msg);
        // }
        
        String t = builder.toString();
        System.out.println(t);

        if ("^C".equals(t.replaceAll("\r|\n", ""))) {
            ctx.close();
        } else {
            ctx.write(Unpooled.wrappedBuffer(t.getBytes()));
            ctx.flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}