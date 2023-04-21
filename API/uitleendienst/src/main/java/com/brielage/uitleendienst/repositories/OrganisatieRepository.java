package com.brielage.uitleendienst.repositories;

import com.brielage.uitleendienst.models.Organisatie;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface OrganisatieRepository extends CrudRepository<Organisatie, String> {
    List<Organisatie> findAll();

    Optional<Organisatie> findById (String id);

    Optional<Organisatie> findByNaam (String naam);

    Optional<Organisatie> findByAdres(String adres);

    Optional<Organisatie> findByTelefoon (String telefoon);

    Optional<Organisatie> findByEmail (String email);
}
