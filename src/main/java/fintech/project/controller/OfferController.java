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

    // show three  offers
    @GetMapping("/getOffersByEnquiryId/{enquiryId}")
    public ResponseEntity<List<Offer>> getOffersByEnquiryId(@PathVariable Long enquiryId) {
        List<Offer> offers = offerService.getOffersByEnquiryId(enquiryId);
        return ResponseEntity.ok(offers);
    }
    // accepted one offer
    @GetMapping("/acceptOffer/{offerId}")
    public ResponseEntity<?> acceptCustomerOffer(@PathVariable Long offerId) {
        Offer selected=offerService.acceptOffer(offerId);
        return ResponseEntity.ok(offerId+" Offer Selected by customer");
    }

    // admin fetch 3 offer on basis of customer enquiry
    @PostMapping("/assignOffersToCustomerBasedOnEnquiry/{enquiryId}")
    public ResponseEntity<List<Offer>> assignOffersToCustomerBasedOnEnquiry(
            @PathVariable Long enquiryId,
            @RequestBody List<Offer> offerList) {

        List<Offer> list = offerService.assignOffersToCustomerBasedOnEnquiry(offerList, enquiryId);
        return ResponseEntity.ok(list);
    }
   
}
