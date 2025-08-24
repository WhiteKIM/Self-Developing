package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.Tag;
import whitekim.self_developing.repository.TagRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    /**
     * 전체 태그리스트 조회
     * @return - 등록된 전체 태그 정보
     */
    public List<String> getTagList() {
        return tagRepository.findAll().stream().map(Tag::getTagName).toList();
    }

    /**
     * 새로운 태그 등록
     * @param tagInfo - 태그명
     */
    public void writeTag(String tagInfo) {
        tagRepository.save(new Tag(tagInfo));
    }

    /**
     * 등록된 태그 제거
     * @param tagName - 제거할 태그명
     */
    public void deleteTag(String tagName) {
        tagRepository.deleteByTagName(tagName);
    }
}
