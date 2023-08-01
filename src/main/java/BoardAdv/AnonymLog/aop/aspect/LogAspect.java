package BoardAdv.AnonymLog.aop.aspect;

import BoardAdv.AnonymLog.logtracer.TraceStatus;
import BoardAdv.AnonymLog.logtracer.logtrace.LogTrace;
import BoardAdv.AnonymLog.logtracer.logtrace.LogTraceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LogAspect {

    private final LogTrace logTrace=new LogTraceImpl();

    @Around("BoardAdv.AnonymLog.aop.pointcuts.LogPointcut.unionPointcut()")
    public Object writeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = logTrace.begin(joinPoint.getSignature().getName());
        try {
            Object proceed = joinPoint.proceed();
            logTrace.end(status);
            return proceed;
        } catch (Exception e) {
            logTrace.exception(status,e);
            throw e;
        }
    }
}
