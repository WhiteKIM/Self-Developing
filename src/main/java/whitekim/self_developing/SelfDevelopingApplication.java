package whitekim.self_developing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("whitekim.self_developing.admin.mapper")
public class SelfDevelopingApplication {
	public static void main(String[] args) {
		SpringApplication.run(SelfDevelopingApplication.class, args);
	}
}
