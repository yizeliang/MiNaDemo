

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.logging.Logger;

/**
 * 继承自IoHandlerAdapter，IoHandlerAdapter继承接口 IoHandler
 * 类IoHandlerAdapter实现了IoHandler的所有方法，只要重载关心的几个方法就可以了
 */
public class MyServerHandler extends IoHandlerAdapter {

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        System.out.println("客户端与服务端连接成功.....");

    }

    int i = 1;

    /*
         * 这个方法是目前这个类里最主要的，
         * 当接收到消息，只要不是quit，就把服务器当前的时间返回给客户端
         * 如果是quit，则关闭客户端连接*/
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        String str = message.toString();
        if (str.trim().equalsIgnoreCase("exit")) {
            sessionClosed(session);
            return;
        }
        System.out.println(str + session.getRemoteAddress());
        i++;
        session.write("返回数据---"+i);
        System.out.println("--------------------我接收到消息--------------------");

        System.out.println(message.toString());
        System.out.println("--------------------消息接收完毕--------------------");
        System.out.println("");
        System.out.println("");
        System.out.println("");

    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        System.out.println("客户端与服务端断开连接.....");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.print("Session 创建成功");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        cause.printStackTrace();
    }
}