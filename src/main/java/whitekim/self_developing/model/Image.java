package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Image extends BaseEntity{
    private String filepath;            // 저장경로
    @Column(unique = true)
    private String filename;            // 파일명
    private String originalFilename;    // 원본파일명
}
