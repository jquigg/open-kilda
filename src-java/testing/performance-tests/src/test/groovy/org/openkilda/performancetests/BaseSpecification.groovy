package org.openkilda.performancetests

import org.openkilda.functionaltests.extension.fixture.SetupOnce
import org.openkilda.functionaltests.extension.healthcheck.HealthCheck
import org.openkilda.functionaltests.helpers.FlowHelper
import org.openkilda.functionaltests.helpers.FlowHelperV2
import org.openkilda.functionaltests.helpers.PathHelper
import org.openkilda.functionaltests.helpers.PortAntiflapHelper
import org.openkilda.messaging.model.system.FeatureTogglesDto
import org.openkilda.messaging.model.system.KildaConfigurationDto
import org.openkilda.messaging.payload.flow.FlowPayload
import org.openkilda.performancetests.helpers.TopologyHelper
import org.openkilda.testing.service.database.Database
import org.openkilda.testing.service.floodlight.FloodlightsHelper
import org.openkilda.testing.service.labservice.LabService
import org.openkilda.testing.service.lockkeeper.LockKeeperService
import org.openkilda.testing.service.northbound.NorthboundService
import org.openkilda.testing.service.northbound.NorthboundServiceV2
import org.openkilda.testing.tools.IslUtils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.text.SimpleDateFormat

@ContextConfiguration(locations = ["classpath:/spring-context.xml"])
class BaseSpecification extends Specification implements SetupOnce {
    @Autowired
    NorthboundService northbound
    @Autowired
    NorthboundServiceV2 northboundV2
    @Autowired
    FloodlightsHelper flHelper
    @Autowired
    FlowHelperV2 flowHelperV2
    @Autowired
    LabService labService
    @Autowired
    LockKeeperService lockKeeper
    @Autowired
    FlowHelper flowHelper
    @Autowired
    @Qualifier("performance")
    TopologyHelper topoHelper
    @Autowired
    Database database
    @Autowired
    IslUtils islUtils
    @Autowired
    PortAntiflapHelper antiflap
    @Autowired
    PathHelper pathHelper

    @Value('${discovery.generic.interval}')
    int discoveryInterval

    @Value('${discovery.timeout}')
    int discoveryTimeout

    @Value('${use.hs}')
    boolean useHs

    @Value('${use.multitable}')
    boolean useMultitable

    @Value('${perf.debug}')
    boolean debug

    /**
     * Use this instead of setupSpec in order to have access to Spring Context and do actions BeforeClass.
     * Can be overridden by inheritor specs.
     * @see {@link org.openkilda.functionaltests.extension.fixture.SetupOnceExtension}
     */
    @Override
    def setupOnce() {
        List<FlowPayload> flows = northbound.getAllFlows()
        int total = flows.size()
        int i = 1
        flows.each {
            log("Removing $i flow from $total")
            i++
            northbound.deleteFlow(it.id)
        }
        topoHelper.purgeTopology()
    }

    @HealthCheck
    def "Configure feature toggles"() {
        expect: "Feature toggles are configured"
        def features = FeatureTogglesDto.builder()
                                        .createFlowEnabled(true)
                                        .updateFlowEnabled(true)
                                        .deleteFlowEnabled(true)
                                        .flowsRerouteOnIslDiscoveryEnabled(true)
                                        .useBfdForIslIntegrityCheck(true)
                                        .build()
        log("updating toggles")
        northbound.toggleFeature(features)
        log("updating kilda configuration")
        northbound.updateKildaConfiguration(KildaConfigurationDto.builder().useMultiTable(useMultitable).build())
        log("Toggles updated")
    }

    def setup() {
        //setup with empty body in order to trigger a SETUP invocation, which is intercepted in several extensions
        //this can have implementation if required
    }

    void log(String message) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z")
        Date date = new Date(System.currentTimeMillis())
        println formatter.format(date) + " " + message
    }
}
