package com.nhworks.poketool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nhworks.poketool.entity.Faborite;

@Repository
public interface FaboriteRepository extends JpaRepository<Faborite, Integer>  {

    List<Faborite> findByRentalId(String rental_id);
    
    List<Faborite> findByMailAddressOrderByRentalId(String mail_address);

    List<Faborite> findByMailAddressAndRentalId(String mail_address, String rental_id);

    @Transactional
    int deleteByMailAddressAndRentalId(String mail_address, String rental_id);
}
