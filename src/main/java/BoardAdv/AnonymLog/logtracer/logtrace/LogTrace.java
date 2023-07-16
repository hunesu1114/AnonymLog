package BoardAdv.AnonymLog.logtracer.logtrace;

import BoardAdv.AnonymLog.logtracer.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
