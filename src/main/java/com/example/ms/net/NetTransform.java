package com.example.ms.net;

import com.example.ms.utils.ByteTransformUtil;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

public class NetTransform {
    public static void positionTransformTest(IoSession session,float[] otherPosition){
        session.write(IoBuffer.wrap(ByteTransformUtil.floatArrayToByteArray(otherPosition)));
    }
}
