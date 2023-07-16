package BoardAdv.AnonymLog.logtracer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TraceStatus {

    private TraceId traceId;    //id, level 담고있음
    private Long startTimeMs;   //시작 시간
    private String message;     //메세지
}
