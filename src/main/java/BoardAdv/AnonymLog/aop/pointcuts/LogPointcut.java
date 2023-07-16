package BoardAdv.AnonymLog.aop.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class LogPointcut {

    @Pointcut("execution(* BoardAdv.AnonymLog.controller.*.*(..))")
    public void allController() {
    }

    @Pointcut("execution(* BoardAdv.AnonymLog.repository.*.*(..))")
    public void allRepository() {
    }

    @Pointcut("execution(* BoardAdv.AnonymLog.service.*.*(..))")
    public void allService() {}

    @Pointcut("allController() || allRepository() || allService()")
    public void unionPointcut() {}
}
