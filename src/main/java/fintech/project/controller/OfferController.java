package fintech.project.controller;

import fintech.project.entity.Offer;
import fintech.project.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @PostMapping("/add")
    public ResponseEntity<?> addOffer(@RequestBody Offer offer) {
        Offer savedOffer = offerService.addOffer(offer);
        return ResponseEntity.ok(savedOffer);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllOffers() {
        List<Offer> offers = offerService.getAllOffer();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        Optional<Offer> optionalOffer = offerService.getOfferById(id);

        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            return ResponseEntity.ok(offer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody Offer offer) {
        try {
            Offer updatedOffer = offerService.updateOffer(id, offer);
            return ResponseEntity.ok(updatedOffer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
