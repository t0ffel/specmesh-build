package io.specmesh.apiparser;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import io.specmesh.apiparser.model.ApiSpec;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class AsyncApiParserTest {
    final ApiSpec apiSpec = getAPISpecFromResource();

    @Test
    public void shouldReturnRootIdWithDelimiter() throws IOException {
        assertThat(apiSpec.id(), is("simple.streetlights"));
    }

    @Test
    public void shouldReturnCanonicalChannelNames() throws IOException {
        final Map<String, String> channelsMap = apiSpec.canonicalChannels();
        assertThat(channelsMap.size(), is(2));
        assertThat("Should have assembled id + channelname", channelsMap.keySet(), hasItem("simple.streetlights.public.light.measured"));
        assertThat("Should use absolute path", channelsMap.keySet(), hasItem("london.hammersmith.transport.public.tube"));
    }


    private ApiSpec getAPISpecFromResource() {
        try {
            return new AsyncApiParser().loadResource(getClass().getClassLoader().getResourceAsStream("test-streetlights-simple-api.yaml"));
        } catch (Throwable t) {
            throw new RuntimeException("Failed to load test resource", t);
        }
    }
}
