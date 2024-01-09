package BoardAdv.AnonymLog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AnonymLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnonymLogApplication.class, args);
	}
}
