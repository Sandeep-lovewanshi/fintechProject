// File: AgentService.java
package fintech.project.service;

import fintech.project.entity.Agent;

public interface AgentService {
    Agent registerAgent(Agent agent);
    Agent loginAgent(String email, String password);
}
