package de.nicolasschlecker.vv.net.interfaces;

import de.nicolasschlecker.vv.domain.models.Message;

import java.io.Closeable;
import java.util.concurrent.Future;

public interface IForwarderReceiver extends Closeable {
    void forward(Message message);

    Future<Message> receive();
}
