package spider.proxy;

import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;


public class SpiderHandler implements InvocationHandler {
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        //TODO 升级方向 暂时没想好怎么写
        return null;
    }
}
