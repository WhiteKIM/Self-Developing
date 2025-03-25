package whitekim.self_developing.service.strategy.impl;

import whitekim.self_developing.model.Paper;
import whitekim.self_developing.service.PaperService;
import whitekim.self_developing.service.strategy.PaperSearchStrategy;

import java.util.List;

public class PaperCategorySearchStrategy implements PaperSearchStrategy {
    private final PaperService paperService;

    public PaperCategorySearchStrategy(PaperService paperService) {
        this.paperService = paperService;
    }

    @Override
    public List<Paper> searchByKeyword(String keyword) {
        return paperService.searchPaperByCategory(keyword);
    }
}
