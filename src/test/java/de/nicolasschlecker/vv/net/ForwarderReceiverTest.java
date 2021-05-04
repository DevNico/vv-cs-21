package de.nicolasschlecker.vv.net;

import de.nicolasschlecker.vv.domain.exceptions.ConnectionException;
import de.nicolasschlecker.vv.domain.models.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ForwarderReceiverTest {
    private final Message message = new Message(Message.Type.SENSOR_HELLO);

    private ForwarderReceiver forwarderReceiver;
    private ByteArrayOutputStream byteArrayOutputStream;
    private ByteArrayInputStream byteArrayInputStream;

    @BeforeEach
    void setup() throws IOException {
        final var socket = mock(Socket.class);
        byteArrayOutputStream = spy(new ByteArrayOutputStream());
        byteArrayInputStream = spy(new ByteArrayInputStream(message.toJson().getBytes()));

        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);

        forwarderReceiver = new ForwarderReceiver(socket);
    }

    @Test
    void forward() {
        forwarderReceiver.forward(message);
        assertThat(byteArrayOutputStream.toString().trim(), equalTo(message.toJson()));
    }

    @Test
    void receive() throws ExecutionException, InterruptedException {
        final var received = forwarderReceiver.receive().get();
        assertThat(received, equalTo(message));
    }

    @Test
    void close() throws IOException {
        forwarderReceiver.close();
        verify(byteArrayOutputStream, times(1)).close();
        verify(byteArrayInputStream, times(1)).close();
    }
}