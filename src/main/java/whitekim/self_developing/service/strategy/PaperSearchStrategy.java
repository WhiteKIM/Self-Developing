package whitekim.self_developing.service.strategy;

import whitekim.self_developing.model.Paper;

import java.util.List;

public interface PaperSearchStrategy {
    List<Paper> searchByKeyword(String keyword);
}
