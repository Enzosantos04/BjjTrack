package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.entity.Scope;
import enzosdev.bjjtrack.exceptions.ScopeNotFoundException;
import enzosdev.bjjtrack.repository.ScopeRepository;
import org.springframework.stereotype.Service;

@Service
public class ScopeService {

    private final ScopeRepository scopeRepository;


    public ScopeService(ScopeRepository scopeRepository) {
        this.scopeRepository = scopeRepository;
    }

    public Scope findById(Long id) {
        return scopeRepository.findById(id).orElseThrow(
                () -> new ScopeNotFoundException("Scope with id " + id + " not found"));
    }


    public Scope findByName(String name) {
        return scopeRepository.findByName(name).orElseThrow(
                () -> new ScopeNotFoundException("Scope with name " + name + " not found"));
    }
}
