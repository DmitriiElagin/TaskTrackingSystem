package elagin.dmitrii.front.service;

import elagin.dmitrii.front.dto.UserDTO;
import elagin.dmitrii.front.entities.User;
import elagin.dmitrii.front.repository.UserRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final RestTemplate restTemplate;

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    public static final String ENDPOINT = "/users";

    @Value("${service.url}")
    private String serviceURL;

    public UserService(RestTemplate restTemplate, UserRepository repository, PasswordEncoder encoder) {
        this.restTemplate = restTemplate;
        this.repository = repository;
        this.encoder = encoder;
    }

    public String getUrl() {
        return serviceURL + ENDPOINT;
    }

    public UserDTO[] requestALL() {
        logger.info("GET Запрос всех пользователей REST-сервиса по адресу {}", getUrl());
        return restTemplate.getForObject(getUrl(), UserDTO[].class);
    }

    public Collection<User> findAll() {
        Collection<User> users = new HashSet<>();

        for (UserDTO dto : requestALL()) {
            repository.findById(dto.getId()).ifPresent(users::add);
        }
        return users;
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public UserDTO save(User user) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request;
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("firstName", user.getFirstName());
        jsonObject.put("lastName", user.getLastName());

        if (user.getId() > 0) {
            jsonObject.put("id", user.getId());
            request = new HttpEntity<>(jsonObject.toString(), headers);

            logger.info("PUT-запрос к REST-сервису по адресу {} для обновления пользователя {} ", getUrl(), user.getUsername());
            restTemplate.put(getUrl(), request);

            repository.save(user);

            logger.info("GET-запрос пользователя с id = {} по адресу {}", user.getId(), getUrl());
            return restTemplate.getForObject(getUrl() + "/" + user.getId(), UserDTO.class);
        }

        request = new HttpEntity<>(jsonObject.toString(), headers);

        logger.info("POST-запрос к REST-сервису по адресу {} для сохранения пользователя {} ", getUrl(), user.getUsername());
        UserDTO dto = restTemplate.postForObject(getUrl(), request, UserDTO.class);
        if (dto != null) {
            user.setId(dto.getId());
            user.setPassword(encoder.encode(user.getPassword()));
        }

        repository.save(user);

        return dto;
    }

    public void delete(User user) {
        logger.info("DELETE-запрос к REST-сервису по адресу {} для удаления пользователя id = {} ", getUrl(), user.getId());
        restTemplate.delete(getUrl() + "/" + user.getId());
        repository.delete(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return optionalUser.get();
    }


}
