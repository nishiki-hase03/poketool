package com.nhworks.poketool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nhworks.poketool.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByMailAddress(String mail_address);
}
