package elagin.dmitry.tasktrackingsystem.service;

import elagin.dmitry.tasktrackingsystem.entities.User;
import elagin.dmitry.tasktrackingsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean existsById(int id) {
        return repository.existsById(id);
    }

    @Transactional
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Optional<User> findById(int id) {
        return repository.findById(id);
    }

    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Transactional
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
