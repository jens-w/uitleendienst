package com.brielage.uitleendienst.controllers;

import com.brielage.uitleendienst.APILogger.APILogger;
import com.brielage.uitleendienst.models.Uitlening;
import com.brielage.uitleendienst.repositories.UitleningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping (value = "/uitlening")
public class UitleningController {
    @SuppressWarnings ("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private UitleningRepository uitleningRepository;

    @GetMapping (value = { "/", "" })
    public ResponseEntity findByProperties (
            @RequestParam (required=false) Date startDatum,
            @RequestParam (required=false) Date eindDatum,
            @RequestParam (required=false) Date teruggebrachtOp,
            @RequestParam (required=false) List<String> organisatieId,
            @RequestParam (required=false) List<String> organisatieNaam,
            @RequestParam (required=false) List<String> magazijnId,
            @RequestParam (required=false) List<String> magazijnNaam){
        if ((startDatum == null || startDatum.isEmpty())
                && (eindDatum == null || eindDatum.isEmpty())
                && (teruggebrachtOp == null || teruggebrachtOp.isEmpty())
                && (organisatieId == null || organisatieId.isEmpty())
                && (organisatieNaam == null || organisatieNaam.isEmpty())
                && (magazijnId == null || magazijnId.isEmpty())
                && (magazijnNaam == null || magazijnNaam.isEmpty())) {
            List<Uitlening> ret = uitleningRepository.findAll();
            if (ret.isEmpty())
                return ResponseEntity.notFound()
                                     .build();
            return ResponseEntity.ok()
                                 .body(ret);
        }

        List<Uitlening> teruggebrachtOpList = new ArrayList<>();
        List<Uitlening> organisatieIdList = new ArrayList<>();
        List<Uitlening> organisatieNaamList = new ArrayList<>();
        List<Uitlening> magazijnIdList = new ArrayList<>();
        List<Uitlening> magazijnNaampList = new ArrayList<>();

        if (teruggebrachtOp != null && !teruggebrachtOp.isEmpty())
            teruggebrachtOpList = uitleningRepository.findByTeruggebrachtOpIsIn(teruggebrachtOp);
        if (organisatieId != null && !organisatieId.isEmpty())
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        APILogger.logRequest("uitlening.findById", id);
        Optional<Uitlening> u = uitleningRepository.findById(id);

        if (u.isPresent())
            return ResponseEntity.ok().body(u.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping (value = { "/", "" })
    public ResponseEntity create (@RequestBody Uitlening uitlening) {
        APILogger.logRequest("uitlening.create", uitlening.toString());
        try {
            if (!validateUitlening(uitlening))
                return ResponseEntity.badRequest().build();

            Uitlening u = uitleningRepository.save(uitlening);

            return new ResponseEntity(u, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity put (@PathVariable String id, @RequestBody Uitlening uitlening) {
        APILogger.logRequest("uitlening.put", id);
        try {
            if (!validateUitleningId(uitlening))
                return ResponseEntity.badRequest().build();

            Optional<Uitlening> u = uitleningRepository.findById(id);

            if (u.isEmpty())
                return ResponseEntity.notFound().build();

            uitlening.setId(u.get().getId());
            Uitlening result = uitleningRepository.save(uitlening);
            return new ResponseEntity(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity delete (@PathVariable String id) {
        APILogger.logRequest("uitlening.delete", id);
        try {
            Optional<Uitlening> u = uitleningRepository.findById(id);

            if (u.isEmpty())
                return ResponseEntity.badRequest().build();

            uitleningRepository.delete(u.get());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean validateUitleningId(Uitlening u) {
        if (u.getId().isEmpty())
            return false;
        return validateUitlening(u);
    }

    private boolean validateUitlening(Uitlening u) {
        return u.getOrganisatie() != null
                && u.getMagazijn() != null
                && u.getStart() != null
                && u.getEind() != null
                && u.getTeruggebrachtOp() != null;
    }
}
