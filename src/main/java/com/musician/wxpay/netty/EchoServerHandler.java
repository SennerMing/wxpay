package com.musician.wxpay.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;

/**
 * @author dingyuxiang
 * @date 2021-03-01 20:17
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelHandlerAdapter {

    int counter = 0;




}
