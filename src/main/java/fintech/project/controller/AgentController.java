// File: AgentController.java
package fintech.project.controller;

import fintech.project.entity.Agent;
import fintech.project.entity.Loan;
import fintech.project.entity.Offer;
import fintech.project.service.AgentService;
import fintech.project.service.impl.LoanServiceImpl;
import fintech.project.service.impl.OfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private LoanServiceImpl loanService;

    @Autowired
    private OfferServiceImpl offerService;

    @PostMapping("/byAgent")
    public ResponseEntity<?> registerAgent(@RequestBody Agent agent) {
        Agent registeredAgent = agentService.registerAgent(agent);
        return ResponseEntity.ok(registeredAgent);
    }

    @PostMapping("/agent/login")
    public ResponseEntity<?> loginAgent(@RequestParam String email,
                                        @RequestParam String password) {
        Agent agent = agentService.loginAgent(email, password);
        if (!agent.getRole().equalsIgnoreCase("AGENT")) {
            return ResponseEntity.status(403).body(" You are not an agent.");
        }
        return ResponseEntity.ok("Agent login successful\n");
    }

    @GetMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@RequestParam String email,
                                        @RequestParam String password) {
        Agent agent = agentService.loginAgent(email, password);

        if (!"ADMIN".equalsIgnoreCase(agent.getRole())) {
            return ResponseEntity.status(403).body(" You are not authorized as admin");
        }
        return ResponseEntity.ok("Admin login successful\n");
    }
    //  agentId all customer
    @GetMapping("/customersAssignedToAgent/{agentId}")
    public ResponseEntity<List<Loan>> getCustomersByAgent(@PathVariable Long agentId) {
        List<Loan> loans = loanService.getLoansByAgentId(agentId);
        return ResponseEntity.ok(loans);
    }

    //admin show selected offer customer
    @GetMapping("selectedOffer")
    public ResponseEntity<List<Offer>> getSelectedOffers() {
        return ResponseEntity.ok(offerService.getAllSelectedOffers());
    }

}

