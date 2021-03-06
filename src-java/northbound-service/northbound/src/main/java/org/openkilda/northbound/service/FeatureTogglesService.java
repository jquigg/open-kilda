/* Copyright 2017 Telstra Open Source
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

package org.openkilda.northbound.service;

import org.openkilda.messaging.model.system.FeatureTogglesDto;

import java.util.concurrent.CompletableFuture;

/**
 * Service to handle feature toggles requests.
 */
public interface FeatureTogglesService {

    /**
     * Changes feature toggles values.
     * @param request configurations of feature toggles to be changed.
     */
    CompletableFuture<FeatureTogglesDto> toggleFeatures(FeatureTogglesDto request);

    /**
     * Method to get information about current feature toggles.
     * @return {@link FeatureTogglesDto} that shows what features are enabled and disabled.
     */
    CompletableFuture<FeatureTogglesDto> getFeatureTogglesState();
}
