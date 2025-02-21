/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.knox.gateway.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.knox.gateway.i18n.GatewayUtilCommonMessages;
import org.apache.knox.gateway.i18n.messages.MessagesFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
  private static final GatewayUtilCommonMessages LOG = MessagesFactory.get( GatewayUtilCommonMessages.class );

  public static String renderAsJsonString(Map<String, Object> map) {
    String json = null;
    ObjectMapper mapper = new ObjectMapper();

    try {
      // write JSON to a file
      json = mapper.writeValueAsString(map);
    } catch ( JsonProcessingException e ) {
      LOG.failedToSerializeMapToJSON( map, e );
    }
    return json;
  }

  public static String renderAsJsonString(Object obj) {
    String json = null;
    ObjectMapper mapper = new ObjectMapper();

    try {
      // write JSON to a file
      json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch ( JsonProcessingException e ) {
      LOG.failedToSerializeObjectToJSON( obj, e );
    }
    return json;
  }

  public static Object getObjectFromJsonString(String json) {
    Map<String, String> obj = null;
    JsonFactory factory = new JsonFactory();
    ObjectMapper mapper = new ObjectMapper(factory);
    TypeReference<Object> typeRef
          = new TypeReference<Object>() {};
    try {
      obj = mapper.readValue(json, typeRef);
    } catch (IOException e) {
      LOG.failedToGetMapFromJsonString( json, e );
    }
    return obj;
  }

  public static Map<String, String> getMapFromJsonString(String json) {
    Map<String, String> map = null;
    JsonFactory factory = new JsonFactory();
    ObjectMapper mapper = new ObjectMapper(factory);
    TypeReference<HashMap<String,String>> typeRef
          = new TypeReference<HashMap<String,String>>() {};
    try {
      map = mapper.readValue(json, typeRef);
    } catch (IOException e) {
      LOG.failedToGetMapFromJsonString( json, e );
    }
    return map;
  }
}
