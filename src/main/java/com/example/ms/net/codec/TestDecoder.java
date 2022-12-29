package com.example.ms.net.codec;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.util.Arrays;

@Slf4j
public class TestDecoder extends CumulativeProtocolDecoder {
    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        int packetHeaderLength = 4;
        if(in.remaining() > packetHeaderLength){
            in.mark();
            int len = in.getInt();
            if(in.remaining() < len - packetHeaderLength){
                //数据包内容不够
                in.reset();
                return false;
            }else {
//                in.reset();
                byte[] packetArray = new byte[len];
                //获取数据包内容
                in.get(packetArray,0,len);
                //收纳
                out.write(packetArray);
                //检查是否粘包
                if(in.remaining()>0){
                    return  true;
                }
            }
        }
        return false;
    }
}
