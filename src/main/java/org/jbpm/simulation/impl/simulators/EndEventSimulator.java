package org.jbpm.simulation.impl.simulators;

import org.jbpm.simulation.ActivitySimulator;
import org.jbpm.simulation.SimulationContext;
import org.jbpm.simulation.SimulationEvent;
import org.jbpm.simulation.impl.events.EndSimulationEvent;
import org.kie.definition.process.Node;
import org.kie.runtime.process.NodeInstance;
import org.kie.runtime.process.ProcessInstance;

public class EndEventSimulator implements ActivitySimulator {

    public SimulationEvent simulate(Object activity, SimulationContext context) {
        NodeInstance nodeInstance = (NodeInstance) activity;
        long startTime = context.getClock().getCurrentTime();
        ProcessInstance pi = nodeInstance.getProcessInstance();
        
        Node node = nodeInstance.getNode();
        String bpmn2NodeId = (String) node.getMetaData().get("UniqueId");
        
        String processName = pi.getProcessName();
        String processVer = pi.getProcess().getVersion();
        if (processVer == null) {
            processVer = "";
        }
        // set end time for processinstance end time
        context.setMaxEndTime(context.getClock().getCurrentTime());
        return new EndSimulationEvent(pi.getProcessId(), pi.getId(), startTime, context.getClock().getCurrentTime(), context.getStartTime(), bpmn2NodeId, node.getName(), processName, processVer);
    }

}
