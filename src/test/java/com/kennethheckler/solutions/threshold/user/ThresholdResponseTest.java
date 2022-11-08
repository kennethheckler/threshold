package com.kennethheckler.solutions.threshold.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ThresholdResponseTest {

    @Autowired
    private JacksonTester<ThresholdResponse> json;

    @Test
    void serialize() throws Exception {
        ThresholdResponse response = new ThresholdResponse(30, new ArrayList<>());
        assertThat(this.json.write(response)).hasJsonPathNumberValue("@.thresholdperc");
        assertThat(this.json.write(response)).extractingJsonPathNumberValue("@.thresholdperc").isEqualTo(response.getThreshold());
    }

    @Test
    void deserialize() throws Exception {
        ThresholdResponse original = new ThresholdResponse(30, new ArrayList<>());
        String content = "{\"thresholdperc\":30,\"users\":[]}";

        assertThat(this.json.parse(content)).isEqualTo(original);
        assertThat(this.json.parseObject(content).getThreshold()).isEqualTo(original.getThreshold());
    }
}
