/* Copyright 2020 Telstra Open Source
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

package org.openkilda.wfm.topology.flowhs.service;

import org.openkilda.messaging.AbstractMessage;
import org.openkilda.messaging.MessageContext;
import org.openkilda.wfm.ICommand;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
public abstract class DbCommand extends AbstractMessage implements ICommand<SpeakerWorkerService> {
    protected final UUID commandId;
    @Setter
    public long sendTime;

    public DbCommand(@NonNull MessageContext messageContext, @NonNull UUID commandId) {
        super(messageContext);
        this.commandId = commandId;
        sendTime = -1;
    }
}
