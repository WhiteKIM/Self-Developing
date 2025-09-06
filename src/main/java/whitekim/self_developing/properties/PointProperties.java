package whitekim.self_developing.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "point")
@Component
@Data
public class PointProperties {
    private long payRate;
}
