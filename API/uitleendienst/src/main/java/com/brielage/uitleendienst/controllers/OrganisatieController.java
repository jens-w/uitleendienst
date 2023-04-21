package com.brielage.uitleendienst.controllers;

import com.brielage.uitleendienst.APILogger.APILogger;
import com.brielage.uitleendienst.models.Organisatie;
import com.brielage.uitleendienst.repositories.OrganisatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping (value = "/organisatie")
public class OrganisatieController {
    @SuppressWarnings ("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private OrganisatieRepository organisatieRepository;

    @GetMapping (value = { "/", "" })
    public ResponseEntity findByProperties (
            @RequestParam (required=false) List<String> naam,
            @RequestParam (required=false) List<String> email){
        List<Organisatie> listNaam = new ArrayList<>();
        List<Organisatie> listEmail = new ArrayList<>();
        
        if ((naam == null || naam.isEmpty())
                && (email == null || email.isEmpty()))  ){
            List<Organisatie> ret = organisatieRepository.findAll();
            if (ret.isEmpty())
                ResponseEntity.notFound().build();
            ResponseEntity.ok().body(ret);
        }

        if (naam != null || !naam.isEmpty())
            listNaam = organisatieRepository.findByNaamIsIn(naam);
        if (email != null || !email.isEmpty())
            listEmail = organisatieRepository.findByEmailIsIn(email);

        // merge lists
        // return
        List<Organisatie> ret = new ArrayList<>();
        if (ret.isEmpty())
            ResponseEntity.notFound().build();
        ResponseEntity.ok().body(ret);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        APILogger.logRequest("organisatie.findById", id);
        Optional<Organisatie> c = organisatieRepository.findById(id);

        if (c.isPresent())
            return ResponseEntity.ok().body(c.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping (value = { "/", "" })
    public ResponseEntity create (@RequestBody Organisatie organisatie) {
        APILogger.logRequest("organisatie.create", organisatie.toString());
        try {
            if (!validateOrganisatie(organisatie))
                return ResponseEntity.badRequest().build();

            Organisatie o = organisatieRepository.save(organisatie);

            return new ResponseEntity(o, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping (value = "/{id}")
    public ResponseEntity put (@PathVariable String id, @RequestBody Organisatie organisatie) {
        APILogger.logRequest("organisatie.put", id);
        try {
            if (!validateOrganisatieId(organisatie))
                return ResponseEntity.badRequest().build();

            Optional<Organisatie> o = organisatieRepository.findById(id);

            if (o.isEmpty())
                return ResponseEntity.notFound().build();

            organisatie.setId(o.get().getId());
            Organisatie result = organisatieRepository.save(organisatie);
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity delete (@PathVariable String id) {
        APILogger.logRequest("organisatie.delete", id);
        try {
            Optional<Organisatie> o = organisatieRepository.findById(id);

            if (o.isEmpty())
                return ResponseEntity.badRequest().build();

            organisatieRepository.delete(o.get());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean validateOrganisatieId(Organisatie o) {
        if (o.getId().isEmpty())
            return false;
        return validateOrganisatie(o);
    }

    private boolean validateOrganisatie(Organisatie o) {
        return !o.getNaam().isEmpty()
                && o.getAdres().isEmpty()
                && o.getEmail().isEmpty()
                && o.getTelefoon().isEmpty();
    }
}
