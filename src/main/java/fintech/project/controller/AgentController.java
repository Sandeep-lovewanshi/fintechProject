// File: AgentController.java
package fintech.project.controller;

import fintech.project.entity.Agent;
import fintech.project.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;


    @PostMapping("/register")
    public ResponseEntity<?> registerAgent(@RequestBody Agent agent) {
        Agent registeredAgent = agentService.registerAgent(agent);
        return ResponseEntity.ok(registeredAgent);

    }


    @PostMapping("/login")
    public ResponseEntity<?> loginAgent(@RequestParam String email,
                                        @RequestParam String password) {
            Agent loggedInAgent = agentService.loginAgent(email, password);
            return ResponseEntity.ok(loggedInAgent);

    }
}
