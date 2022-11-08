package com.kennethheckler.solutions.threshold.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserResponseTest {

    @Autowired
    private JacksonTester<UserResponse> json;

    @Test
    void serialize() throws Exception {
        UserResponse response = new UserResponse(1, 10, 19, 2, new ArrayList<>());
        assertThat(this.json.write(response)).hasJsonPathNumberValue("@.page");
        assertThat(this.json.write(response)).extractingJsonPathNumberValue("@.page").isEqualTo(response.getPage());
    }

    @Test
    void deserialize() throws Exception {
        UserResponse original = new UserResponse(1, 10, 19, 2, new ArrayList<>());
        String content = "{\"page\":1,\"per_page\":10,\"total\":19,\"total_pages\":2,\"data\":[]}";

        assertThat(this.json.parse(content)).isEqualTo(original);
        assertThat(this.json.parseObject(content).getPage()).isEqualTo(original.getPage());
    }
}