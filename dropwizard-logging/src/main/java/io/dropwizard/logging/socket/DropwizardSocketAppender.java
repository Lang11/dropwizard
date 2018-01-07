package io.dropwizard.logging.socket;

import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.recovery.ResilentSocketOutputStream;
import ch.qos.logback.core.spi.DeferredProcessingAware;

import javax.net.SocketFactory;
import java.io.OutputStream;

/**
 * Sends log events to a TCP server, a connection to which is represented as {@link ResilentSocketOutputStream}.
 */
public class DropwizardSocketAppender<E extends DeferredProcessingAware> extends OutputStreamAppender<E> {

    public DropwizardSocketAppender(String host, int port, int connectionTimeoutMs, int sendBufferSize,
                                    SocketFactory socketFactory) {
        setOutputStream(socketOutputStream(host, port, connectionTimeoutMs, sendBufferSize, socketFactory));
    }

    protected OutputStream socketOutputStream(String host, int port, int connectionTimeoutMs, int sendBufferSize,
                                              SocketFactory socketFactory) {
        final ResilentSocketOutputStream outputStream = new ResilentSocketOutputStream(host, port, connectionTimeoutMs,
            sendBufferSize, socketFactory);
        outputStream.setContext(context);
        return outputStream;
    }
}

