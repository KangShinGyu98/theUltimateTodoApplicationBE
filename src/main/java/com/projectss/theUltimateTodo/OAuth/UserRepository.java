package com.projectss.theUltimateTodo.OAuth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    public Optional<User> findById(String userId);
    public Boolean existsUserByOpenId(String openId);

}
