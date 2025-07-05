package fintech.project.service.impl;

import fintech.project.entity.Agent;
import fintech.project.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgentService implements fintech.project.service.AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public Agent registerAgent(Agent agent) {

        Optional<Agent> existingAgent = agentRepository.findByEmail(agent.getEmail());
        if (existingAgent.isPresent()) {
            throw new RuntimeException("Agent with this email already exists.");
        }

        return agentRepository.save(agent);
    }

    @Override
    public Agent loginAgent(String email, String password) {

        Optional<Agent> agentOpt = agentRepository.findByEmail(email);

        if (agentOpt.isEmpty()) {
            throw new RuntimeException("Agent not found.");
        }

        Agent agent = agentOpt.get();

        if (!agent.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password.");
        }

        return agent;
    }
}
