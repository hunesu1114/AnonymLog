package BoardAdv.AnonymLog.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Slf4j
public class LogAspect {

    @Around("BoardAdv.AnonymLog.aop.pointcuts.LogPointcut.unionPointcut()")
    public Object writeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("==========START : {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

}
