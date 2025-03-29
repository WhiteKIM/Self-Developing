package whitekim.self_developing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whitekim.self_developing.model.Page;
import whitekim.self_developing.repository.PageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PageService {
    private final PageRepository pageRepository;

    public void registerPage() {

    }

    public Page getDetailPage() {
        return null;
    }

    public List<Page> getPageList() {
        return null;
    }

    public void deletePage() {

    }
}
