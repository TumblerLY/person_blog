package com.irm.blog.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Tumbler
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());


    //在那个地方进行切面跟踪
    @Pointcut("execution(* com.irm.blog.web.*.*(..))")
    public void log(){

    }

    //切面开始之前
   @Before("log()")
    public void deBefore(JoinPoint joinPoint){
       ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       HttpServletRequest request=attributes.getRequest();
       String url=request.getRequestURL().toString();
       String ip=request.getRemoteAddr();
       String classMethod=joinPoint.getSignature()
                          .getDeclaringTypeName()+"。"+
                           joinPoint.getSignature().getName();
       Object[] args=joinPoint.getArgs();
       RequestLog requestLog=new RequestLog(url,ip,classMethod,args);
            logger.info("request:{}",requestLog);
    }

    //切面开启之后
    @After("log()")
    public void doAfter(){
             logger.info("-------------doafter-----------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result:{}"+result);

    }
    private class RequestLog{

        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }
        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
