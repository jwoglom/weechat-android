/*******************************************************************************
 * Copyright 2014 Matthew Horan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.ubergeek42.weechat.relay.messagehandler;

import com.ubergeek42.weechat.relay.RelayMessageHandler;
import com.ubergeek42.weechat.relay.connection.IConnection;
import com.ubergeek42.weechat.relay.protocol.RelayObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PongHandler implements RelayMessageHandler {
    private static Logger logger = LoggerFactory.getLogger("PongHandler");
    final private static boolean DEBUG = false;

    private IConnection conn;
    private long lastPong;

    public PongHandler(IConnection conn) {
        this.conn = conn;
    }

    @Override
    public void handleMessage(RelayObject obj, String id) {
        if (DEBUG) logger.debug("handleMessage _pong: " + obj);
        setLastPong(Long.parseLong(obj.asString()));
    }

    synchronized private void setLastPong(long lastPong) {
        this.lastPong = lastPong;
        notify();
    }

    synchronized public void waitForPong(long ping) throws InterruptedException {
        if (DEBUG) logger.debug("waitForPong ping: " + ping);
        long timeout = ping + 60 * 1000;
        long lastPong = this.lastPong;
        while (lastPong != ping && System.currentTimeMillis() < timeout) {
            wait(timeout - System.currentTimeMillis());
            lastPong = this.lastPong;
        }

        if (DEBUG) logger.debug("waitForPing lastPong: " + lastPong);
        if (lastPong != ping) {
            if (DEBUG) logger.debug("waitForPing timed out waiting for: " + ping);
            conn.disconnect();
        }
    }
}
