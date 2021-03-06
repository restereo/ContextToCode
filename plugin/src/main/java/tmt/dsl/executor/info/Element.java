/*
 * Copyright (C) 2012 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package tmt.dsl.executor.info;

public class Element {

  private String key;
  private Object value;
  private String type;
  private boolean visible = true;
  
  public Element(String k, Object v, String t, boolean vis_, String type_) {
    key = k;
    value = v;
    type = type_;
    visible = vis_;
  }
  
  public String getKey() {
    return key;
  }

  public Object getValue() {
    return value;
  }
  
  public String getType() {
    return type;
  }
  
  public String toString () {
    String ret;
    
    if (visible)
        ret = "key: "+key+" value: "+value+" type: "+type+" visible: "+visible;
    else
        ret = "key: "+key+" type: "+type+" visible: "+visible;
    return ret;
  }
  
  public boolean isVisible() {
    return visible;
  }
}
