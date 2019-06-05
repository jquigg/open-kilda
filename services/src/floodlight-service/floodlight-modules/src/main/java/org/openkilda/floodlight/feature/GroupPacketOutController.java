/* Copyright 2019 Telstra Open Source
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

package org.openkilda.floodlight.feature;

import org.openkilda.messaging.model.SpeakerSwitchView;
import org.openkilda.messaging.model.SpeakerSwitchView.Feature;

import net.floodlightcontroller.core.IOFSwitch;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class GroupPacketOutController extends AbstractFeature {

    public static final String CENTEC_MANUFACTURED = "Centec";

    @Override
    public Optional<SpeakerSwitchView.Feature> discover(IOFSwitch sw) {
        Optional<SpeakerSwitchView.Feature> empty = Optional.empty();
        if (StringUtils.containsIgnoreCase(
                sw.getSwitchDescription().getManufacturerDescription(), CENTEC_MANUFACTURED)) {
            return empty;
        }

        return Optional.of(Feature.GROUP_PACKET_OUT_CONTROLLER);
    }
}
