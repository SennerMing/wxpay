package com.musician.wxpay.netty.serializable.DecoderAndEncoder;

import io.netty.channel.ChannelHandlerAdapter;

/**
 * @author dingyuxiang
 * @date 2021-03-02 15:25
 */
public class EchoClientHandler extends ChannelHandlerAdapter {
    private final int sendNumber;
    public EchoClientHandler(int sendNumber){
        this.sendNumber = sendNumber;
    }


}
