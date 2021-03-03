package com.musician.wxpay.netty.serializable;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author dingyuxiang
 * @date 2021-03-02 13:46
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -8523518445353638746L;

    private String userName;
    private int userId;

    public UserInfo buildUserName(String userName){
        this.userName = userName;
        return this;
    }
    public UserInfo buildUserId(int userId){
        this.userId = userId;
        return this;
    }

    public final String getUserName(){
        return userName;
    }

    public final void serUserName(){
        this.userName = userName;
    }

    public final int getUserId(){
        return userId;
    }

    public final void setUserId(int userId){
        this.userId = userId;
    }

    public byte[] codeC(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] value = this.userName.getBytes();
        byteBuffer.putInt(value.length);
        byteBuffer.put(value);
        byteBuffer.putInt(this.getUserId());
        byteBuffer.flip();
        value = null;
        byte[] result = new byte[byteBuffer.remaining()];
        byteBuffer.get(result);
        return result;
    }

    public byte[] codeC(ByteBuffer byteBuffer){
        byteBuffer.clear();
        byte[] value = this.userName.getBytes();
        byteBuffer.putInt(value.length);
        byteBuffer.put(value);
        byteBuffer.putInt(this.getUserId());
        byteBuffer.flip();
        value = null;
        byte[] result = new byte[byteBuffer.remaining()];
        byteBuffer.get(result);
        return result;
    }
}
