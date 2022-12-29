package com.example.ms.net;

import com.example.ms.game.MapleCharacter;
import com.example.ms.game.MapleClient;
import com.example.ms.game.manager.MapManager;
import com.example.ms.utils.ByteTransformUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Slf4j
public class MinaServerHandler extends IoHandlerAdapter {
    private final SimpleDateFormat sdfh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        log.error("异常..."+cause);
    }

    @Override
    public void messageSent(IoSession session,Object message)throws Exception{
//        byte[] msg = (byte[]) message;
//        float[] array = ByteTransformUtil.byteArrayToFloatArray(msg);
//        log.info("向玩家{}发送{}",session.getAttribute("num"),Arrays.toString(array));
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        byte[] msg = (byte[]) message;
        float[] array = ByteTransformUtil.byteArrayToFloatArray(msg);
//        log.info(Arrays.toString(array));
        if(array.length == 2){
            int clientId = (int) session.getAttribute("num");
            if(MapManager.getInstance().allMaps.get(0).getClients().containsKey(clientId)){
                Server.onlineClients.get(clientId).getMapleCharacter().setPosition(array);
            }else {
                MapManager.getInstance().allMaps.get(0).getClients().put(clientId,Server.onlineClients.get(clientId));
                MapManager.getInstance().activeOneMap(0);
                log.info("玩家{}进入地图{}",clientId,0);
            }
        }

//        log.info("收到来自"+session.getRemoteAddress()+" 的消息："+ Arrays.toString(array));
//        Date date = new Date();
//        session.write(IoBuffer.wrap(("hello! "+sdfh.format(date)).getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void sessionCreated(IoSession session) {
        log.info("服务端与客户端创建连接...{}",session.getRemoteAddress());
        Map<Integer, MapleClient> tmp = Server.onlineClients;
        for(int i = 0;i<=tmp.size(); i++){
            if(!tmp.containsKey(i)){
                tmp.put(i,new MapleClient(session,new MapleCharacter()));
                session.setAttribute("num",i);
                break;
            }
        }
        log.info("MapleClient初始化编号：{}",session.getAttribute("num"));
    }

    @Override
    public void sessionClosed(IoSession session) {
        log.info("服务端关闭连接...");
    }

    @Override
    public void inputClosed(IoSession session) {
        log.info("客户端关闭连接...{}",session.getRemoteAddress());
        session.closeNow();
    }

}
