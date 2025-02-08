package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.repository.CertRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CertService {
    private final CertRepository certRepository;

    public void saveCertification(Certification cert) {
        certRepository.save(cert);
    }

    public Certification findByCertificationName(String certificationName) {
        Optional<Certification> optCert = certRepository.findByCertName(certificationName);

        if(optCert.isEmpty())
            throw new RuntimeException();

        return optCert.get();
    }
}
