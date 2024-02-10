package org.ichat.backend.service.implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.ichat.backend.exeception.AccountException;
import org.ichat.backend.model.User;
import org.ichat.backend.repository.UserRepo;
import org.ichat.backend.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepo userRepo;

    @Override
    public User findBy(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new AccountException("User not found by id"));
    }

    @Override
    public User findBy(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new AccountException("User not found by email"));
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User add(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent())
            throw new AccountException("The provided email is already registered");
        if (userRepo.findByUsername(user.getUsername()).isPresent())
            throw new AccountException("The provided username is already taken");

        return userRepo.save(user);
    }

    @Override
    public void deleteBy(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User update(Long id, User user) throws AccountException {
        User userToUpdate = userRepo.findById(id).orElseThrow(() -> new AccountException("User not found"));

        if (user.getFirst_name() != null)
            userToUpdate.setFirst_name(user.getFirst_name());
        if (user.getLast_name() != null)
            userToUpdate.setLast_name(user.getLast_name());
        if (user.getEmail() != null)
            userToUpdate.setEmail(user.getEmail());
        if (user.getAddress() != null)
            userToUpdate.setAddress(user.getAddress());
        if (user.getPhone() != null)
            userToUpdate.setPhone(user.getPhone());
        if (user.getImage_url() != null)
            userToUpdate.setImage_url(user.getImage_url());
        if (user.getEnabled() != null)
            userToUpdate.setUsing_mfa(user.getUsing_mfa());

        return userRepo.save(userToUpdate);
    }

}
