/*
 * Copyright 2019 Telstra Open Source
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.openkilda.floodlight.command.flow.transit;

import org.openkilda.floodlight.api.FlowTransitEncapsulation;
import org.openkilda.floodlight.command.flow.FlowSegmentReport;
import org.openkilda.floodlight.service.session.Session;
import org.openkilda.messaging.MessageContext;
import org.openkilda.model.Cookie;
import org.openkilda.model.SwitchId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import org.projectfloodlight.openflow.protocol.OFFactory;
import org.projectfloodlight.openflow.protocol.OFFlowMod;
import org.projectfloodlight.openflow.protocol.OFFlowMod.Builder;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.types.OFPort;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TransitFlowSegmentInstallCommand extends TransitFlowSegmentBlankCommand {
    @JsonCreator
    public TransitFlowSegmentInstallCommand(
            @JsonProperty("message_context") MessageContext context,
            @JsonProperty("switch_id") SwitchId switchId,
            @JsonProperty("command_id") UUID commandId,
            @JsonProperty("flowid") String flowId,
            @JsonProperty("cookie") Cookie cookie,
            @JsonProperty("ingressIslPort") Integer ingressIslPort,
            @JsonProperty("egressIslPort") Integer egressIslPort,
            @JsonProperty("encapsulation") FlowTransitEncapsulation encapsulation) {
        super(context, switchId, commandId, flowId, cookie, ingressIslPort, egressIslPort, encapsulation);
    }

    @Override
    protected Builder makeFlowModBuilder(OFFactory of) {
        return makeFlowAddBuilder(of);
    }
}