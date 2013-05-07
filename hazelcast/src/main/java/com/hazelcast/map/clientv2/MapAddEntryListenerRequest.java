/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.map.clientv2;

import com.hazelcast.clientv2.AbstractClientRequest;
import com.hazelcast.clientv2.ClientRequest;
import com.hazelcast.concurrent.lock.LockOperation;
import com.hazelcast.core.EntryListener;
import com.hazelcast.instance.ThreadContext;
import com.hazelcast.map.MapPortableHook;
import com.hazelcast.map.MapService;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.spi.DefaultObjectNamespace;
import com.hazelcast.spi.ObjectNamespace;

import java.io.IOException;

public class MapAddEntryListenerRequest extends AbstractClientRequest implements ClientRequest {

    private String name;
    private EntryListener listener;
    private Data key;
    private boolean includeValue;

    public MapAddEntryListenerRequest() {
    }

    public MapAddEntryListenerRequest(String name, EntryListener listener, Data key, boolean includeValue) {
        this.name = name;
        this.listener = listener;
        this.includeValue = includeValue;
        this.key = key;
    }

    public Object process() throws Exception {
        // todo implement
        return null;
    }

    public String getServiceName() {
        return MapService.SERVICE_NAME;
    }

    @Override
    public int getFactoryId() {
        return MapPortableHook.F_ID;
    }

    public int getClassId() {
        return MapPortableHook.ADD_ENTRY_LISTENER;
    }

    public void writePortable(PortableWriter writer) throws IOException {
        writer.writeUTF("n", name);
        writer.writeBoolean("i", includeValue);
        final ObjectDataOutput out = writer.getRawDataOutput();
        key.writeData(out);
        out.writeObject(listener);
    }

    public void readPortable(PortableReader reader) throws IOException {
        name = reader.readUTF("n");
        includeValue = reader.readBoolean("i");
        final ObjectDataInput in = reader.getRawDataInput();
        key = new Data();
        key.readData(in);
        listener = in.readObject();
    }
}