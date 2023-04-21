package com.brielage.uitleendienst.controllers;

import com.brielage.uitleendienst.APILogger.APILogger;
import com.brielage.uitleendienst.models.Exmpl;
import com.brielage.uitleendienst.models.Persoon;
import com.brielage.uitleendienst.repositories.PersoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping (value = "/persoon")
public class PersoonController {
    @SuppressWarnings ("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private PersoonRepository persoonRepository;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity findByProperties (
            @RequestParam (required = false) List<String> voornaam,
            @RequestParam (required = false) List<String> familienaam,
            @RequestParam (required = false) List<String> adres,
            @RequestParam (required = false) List<String> telefoon,
            @RequestParam (required = false) List<String> email) {
        APILogger.logRequest("persoon.findByProperties");
        List<Persoon> returnValue = new ArrayList<>();;

        //return findAll() if no properties
        if ((voornaam == null || voornaam.isEmpty())
                && (familienaam == null || familienaam.isEmpty())
                && (email == null || email.isEmpty())) {
            returnValue = persoonRepository.findAll();

            if (returnValue.isEmpty())
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(returnValue);
        }

        //add all elements found by the properties to returnValue
        if (voornaam != null && !voornaam.isEmpty())
            returnValue.addAll(persoonRepository.findAllByVoornaamIsIn(voornaam));
        if (familienaam != null && !familienaam.isEmpty())
            returnValue.addAll(persoonRepository.findAllByFamilienaamIsIn(familienaam));
        if (adres != null && !adres.isEmpty())
            returnValue.addAll(persoonRepository.findAllByAdresIsIn(adres));
        if (telefoon != null && !telefoon.isEmpty())
            returnValue.addAll(persoonRepository.findAllByTelefoonIsIn(telefoon));
        if (email != null && !email.isEmpty())
            returnValue.addAll(persoonRepository.findAllByEmailIsIn(email));

        if (returnValue.isEmpty())
            return ResponseEntity.notFound().build();

        //remove duplicates
        returnValue = RemoveDuplicatesController.removeDuplicates(returnValue);

        return ResponseEntity.ok().body(returnValue);
    }

    @GetMapping ("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        APILogger.logRequest("persoon.findById", id);
        Optional<Persoon> p = persoonRepository.findById(id);

        if (p.isPresent())
            return ResponseEntity.ok().body(p.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping (value = { "/", "" })
    public ResponseEntity create (@RequestBody Persoon persoon) {
        APILogger.logRequest("persoon.create", persoon.toString());
        try {
            if (!validatePersoon(persoon))
                return ResponseEntity.badRequest().build();

            Persoon p = persoonRepository.save(persoon);

            return new ResponseEntity(p, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping (value = "/{id}")
    public ResponseEntity put (@PathVariable String id, @RequestBody Persoon persoon) {
        APILogger.logRequest("persoon.put", id);
        try {
            if (!validatePersoonId(persoon))
                return ResponseEntity.badRequest().build();

            Optional<Persoon> p = persoonRepository.findById(id);

            if (p.isEmpty())
                return ResponseEntity.notFound().build();

            persoon.setId(p.get().getId());
            Persoon result = persoonRepository.save(persoon);
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity delete (@PathVariable String id) {
        APILogger.logRequest("persoon.delete", id);
        try {
            Optional<Persoon> p = persoonRepository.findById(id);

            if (p.isEmpty())
                return ResponseEntity.badRequest().build();

            persoonRepository.delete(p.get());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean validatePersoonId (Persoon p) {
        if (p.getId().isEmpty())
            return false;
        return validatePersoon(p);
    }

    private boolean validatePersoon (Persoon p) {
        return !p.getVoornaam().isEmpty()
                && !p.getFamilienaam().isEmpty()
                && !p.getAdres().isEmpty()
                && !p.getTelefoon().isEmpty()
                && !p.getEmail().isEmpty();
    }
}
