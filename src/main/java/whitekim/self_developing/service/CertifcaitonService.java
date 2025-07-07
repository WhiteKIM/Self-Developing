package whitekim.self_developing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.repository.CertRepository;
import whitekim.self_developing.service.factory.problem.ProblemServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CertifcaitonService {

    private final CertRepository certRepository;
    private final ProblemServiceFactory serviceFactory;

    public Problem selectRandomProblemByCertification (String certName) {
        List<Problem> problemList = new ArrayList<>();

        for(ProblemService<? extends Problem> service : serviceFactory.getService()) {
            Problem problem = service.searchRandomProblemByCertification(certName);

            if(problem != null) {
                problemList.add(problem);
            }
        }

        return problemList.get(new Random().nextInt() % problemList.size());
    }
}
