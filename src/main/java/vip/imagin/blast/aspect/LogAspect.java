package vip.imagin.blast.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * 这是一个记录日志的切面类
 */
@Aspect
@Component //必须让spring扫描到
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* vip.imagin.blast.modules.user.controller.*.*(..))  || execution(* vip.imagin.blast.modules.meterial.controller.*.*(..))")//切点表达式 切Controller下的所有方法
    public void log(){

    }

    //这里返回了一些用户的基本信息 ip url等.....
    @Before("log()")
    public void  doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //得到Httprequest对象
        HttpServletRequest request = attr.getRequest();

        /////////////////////////////////////浏览器
//        String referer = request.getHeader("referer");
//        logger.info("代理：{}",referer);
        ////////////////////////////////////
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //使用johnPoint获取类和方法
        String method = joinPoint.getSignature().getDeclaringTypeName()+'.'+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog logs = new RequestLog(url, ip, method, args);
        System.out.println(logs);
        logger.info("用户的各种日志：{}",logs);
    }

    @AfterReturning(returning = "o",pointcut = "log()")
    public void doAfterreturn(Object o){ //joinpoint aop 中获取信息的
        logger.info("返回给用户的数据：{}",o);
    }

    //日志类， 用于记录url 访问者的ip 访问的方法名 以及方法参数
    private class RequestLog{
        private String url;
        private String ip;
        private String Method;
        private Object[] args;
        public RequestLog() {
        }

        public RequestLog(String url, String ip, String method, Object[] args) {
            this.url = url;
            this.ip = ip;
            Method = method;
            this.args = args;
        }

        public String getUrl() {
            return url;
        }

        public String getIp() {
            return ip;
        }

        public String getMethod() {
            return Method;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public void setMethod(String method) {
            Method = method;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", Method='" + Method + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }





}
