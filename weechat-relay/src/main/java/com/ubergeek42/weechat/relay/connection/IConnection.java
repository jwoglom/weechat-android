package com.ubergeek42.weechat.relay.connection;

import com.ubergeek42.weechat.relay.RelayConnectionHandler;

import java.io.IOException;

public interface IConnection {
    void addConnectionHandler(RelayConnectionHandler relayConnection);

    enum STATE {
        CONNECTING,
        CONNECTED,
        AUTHENTICATED,
        DISCONNECTED,
    }

    public void connect();

    public void disconnect();

    public boolean isConnected();

    int read(byte[] bytes, int off, int len) throws IOException;

    void write(byte[] bytes);

    void notifyHandlersOfError(Exception e);

    void notifyHandlers(STATE s);

    public void enablePing(boolean enabled);

    public boolean pingEnabled();

    public void setPingTimeout(int pingTimeout);

    public long pingTimeout();
}
