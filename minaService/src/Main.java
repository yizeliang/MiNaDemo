import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class Main {
    private static final int PORT = 9090;

    public static void main(String[] args) {

        //创建连接器
        IoAcceptor acceptor = new NioSocketAcceptor();

        //添加日志管理过滤器
        acceptor.getFilterChain().addLast("Logger", new LoggingFilter());

        // 协议解析，采用mina现成的UTF-8字符串处理方式
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        // 设置消息处理类（创建、关闭Session，可读可写等等，继承自接口IoHandler）
        acceptor.setHandler(new MyServerHandler());
        // 设置接收缓存区大小
        acceptor.getSessionConfig().setReadBufferSize(2048);
        //回到空闲状态的时间
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        try {
            // 服务器开始监听
            acceptor.bind(new InetSocketAddress("192.168.191.1",PORT));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
