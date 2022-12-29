package com.example.ms.net;

import com.example.ms.game.MapleClient;
import com.example.ms.game.manager.MapManager;
import com.example.ms.net.codec.TestCodecFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

@Slf4j
public class Server {
//    private final Logger log = LoggerFactory.getLogger(Server.class);
    private static Server instance = null;
    private static int serverPort = 8080;
    public static Map<Integer, MapleClient> onlineClients = new HashMap<>();

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public static void main(String args[]) {
        Server.getInstance().init();
    }

    public void init() {
        ThreadManager.getInstance().start();
        MapManager.getInstance().init();
        MapManager.getInstance().start();
        try {
            // 创建一个非阻塞的server端的Socket
            IoAcceptor acceptor = new NioSocketAcceptor();
            // 设置过滤器
            acceptor.getFilterChain().addLast("codec",
                    new ProtocolCodecFilter(new TestCodecFactory()));
            // 设置读取数据的缓冲区大小
            acceptor.getSessionConfig().setReadBufferSize(20480);
            // 读写通道10秒内无操作进入空闲状态
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            // 绑定逻辑处理器
            acceptor.setHandler(new MinaServerHandler());
            // 绑定端口
            acceptor.bind(new InetSocketAddress(serverPort));
            log.info("服务端启动成功... 端口号为：" + serverPort);
        } catch (Exception e) {
            log.error("服务端启动异常...."+e);
            e.printStackTrace();
        }
    }

}
