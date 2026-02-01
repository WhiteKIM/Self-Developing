package whitekim.self_developing.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {
    private Long userId;
    private String username;
    private LocalDateTime atCreated;
    private LocalDateTime latestAccessTime;
}
