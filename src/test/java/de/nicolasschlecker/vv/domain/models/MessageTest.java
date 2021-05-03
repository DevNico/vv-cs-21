package de.nicolasschlecker.vv.domain.models;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Message model tests")
class MessageTest {
    @ParameterizedTest
    @EnumSource(Message.Type.class)
    void fromJson_toJson(Message.Type type) {
        final var message = new Message(type);
        final var json = message.toJson();
        final var fromJsonMessage = Message.fromJson(json);
        assertThat(message, equalTo(fromJsonMessage));

        final var message2 = new Message(type, "payload");
        final var json2 = message2.toJson();
        final var fromJsonMessage2 = Message.fromJson(json2);
        assertThat(message2, equalTo(fromJsonMessage2));
    }


    @ParameterizedTest
    @EnumSource(Message.Type.class)
    void testToString(Message.Type type) {
        final var message = new Message(type);
        assertThat(String.format("Message{type=%s, payload=''}", type.toString()), equalTo(message.toString()));
    }

    @Test
    void testEquals() {
        EqualsVerifier.simple().forClass(Message.class).verify();
    }
}