package whitekim.self_developing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.problem.Problem;
import whitekim.self_developing.repository.CertRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertRepository certRepository;
    private final ProblemService problemService;

    public Problem selectRandomProblemByCertification (String certName) {
        List<Problem> problemList = new ArrayList<>();

        Problem problem = problemService.searchRandomProblemByCertification(certName);

        if(problem != null) {
            problemList.add(problem);
        }

//        return problemList.get(new Random().nextInt() % problemList.size());//

        return null;
    }
}
