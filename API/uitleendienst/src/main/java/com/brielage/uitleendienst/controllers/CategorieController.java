package com.brielage.uitleendienst.controllers;

import com.brielage.uitleendienst.APILogger.APILogger;
import com.brielage.uitleendienst.models.Categorie;
import com.brielage.uitleendienst.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping(value = "/categorie")
public class CategorieController {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping (value = { "/", "" })
    public ResponseEntity findByProperties (@RequestParam (required=false) List<String> naam){
        if (naam != null || naam.isEmpty()) {
            list<Categorie> ret = categorieRepository.findAll();
            if (ret.isEmpty())
                return ResponseEntity.notFound()
                                     .build();
            return ResponseEntity.ok()
                                 .body(ret);
        }

        List<Categorie> ret = categorieRepository.findByNaamIsIn(naam);

        if (ret.isEmpty())
            return ResponseEntity.notFound()
                                 .build();
        return ResponseEntity.ok()
                             .body(ret);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        APILogger.logRequest("categorie.findById", id);
        Optional<Categorie> c = categorieRepository.findById(id);

        if (c.isPresent())
            return ResponseEntity.ok().body(c.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping (value = { "/", "" })
    public ResponseEntity create(@RequestBody Categorie categorie) {
        APILogger.logRequest("categorie.create", categorie.toString());
        try {
            if (!validateCategorie(categorie))
                return ResponseEntity.badRequest().build();

            Categorie c = categorieRepository.save(categorie);

            return new ResponseEntity(c, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping (value = "/{id}")
    public ResponseEntity put (@PathVariable String id, @RequestBody Categorie categorie) {
        APILogger.logRequest("categorie.put", id);
        try {
            if (!validateCategorieId(categorie))
                return ResponseEntity.badRequest().build();

            Optional<Categorie> c = categorieRepository.findById(id);

            if (c.isEmpty())
                return ResponseEntity.notFound().build();

            categorie.setId(c.get().getId());
            Categorie result = categorieRepository.save(categorie);
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity delete (@PathVariable String id) {
        APILogger.logRequest("categorie.delete", id);
        try {
            Optional<Categorie> c = categorieRepository.findById(id);

            if (c.isEmpty())
                return ResponseEntity.badRequest().build();

            categorieRepository.delete(c.get());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean validateCategorieId(Categorie c) {
        if (c.getId().isEmpty())
            return false;
        return validateCategorie(c);
    }

    private boolean validateCategorie(Categorie c) {
        return !c.getNaam().isEmpty();
    }
}
