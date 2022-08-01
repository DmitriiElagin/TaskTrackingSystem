package elagin.dmitry.tasktrackingsystem.service;

import elagin.dmitry.tasktrackingsystem.entities.User;
import elagin.dmitry.tasktrackingsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository dao;

    public UserService(UserRepository dao) {
        this.dao = dao;
    }

    @Transactional
    public Iterable<User> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Optional<User> findById(int id) {
        return dao.findById(id);
    }

    @Transactional
    public User save(User user) {
        return dao.save(user);
    }

    @Transactional
    public void deleteById(int id) {
        dao.deleteById(id);
    }
}
