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
import com.ubergeek42.weechat.relay.protocol.RelayObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LastMessageHandler implements RelayMessageHandler {
    private static Logger logger = LoggerFactory.getLogger("LastMessageHandler");
    final private static boolean DEBUG = false;

    private volatile long lastMessageReceivedAt = 0;

    @Override
    public void handleMessage(RelayObject obj, String id) {
        lastMessageReceivedAt = System.currentTimeMillis();
    }

    public long lastMessageReceivedAt() {
        return lastMessageReceivedAt;
    }
}
