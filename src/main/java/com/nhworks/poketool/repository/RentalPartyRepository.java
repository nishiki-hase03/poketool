package com.nhworks.poketool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nhworks.poketool.entity.RentalParty;
import java.util.List;


@Repository
public interface RentalPartyRepository extends JpaRepository<RentalParty, Integer> {
    List<RentalParty> findByRentalId(String rental_id);

    List<RentalParty> findByMailAddressOrderByRentalId(String mail_address);

    List<RentalParty> findByIntroduceLike(String keyword);

    @Query(value = "SELECT * FROM rentalparty ORDER BY random() LIMIT 1",
        nativeQuery = true)
    RentalParty getRandomParty();
}
