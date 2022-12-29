package com.example.ms.net.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class TestCodecFactory implements ProtocolCodecFactory {
    private final TestEncoder encoder;
    private final TestDecoder decoder;
    public TestCodecFactory(){
        encoder = new TestEncoder();
        decoder = new TestDecoder();
    }
    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
}
