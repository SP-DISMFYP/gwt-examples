package com.gawkat.core.client.account.thingstuff;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingStuffData implements IsSerializable {
  
  private long thingStuffId = 0;

  // what type of data is it?
  private long thingStuffTypeId = 0;

  // owner
  private long thingId = 0;
  
  // values that can be stored
  private String value = null;
  private boolean valueBol = false;
  private double valueDouble = 0.0;
  private long valueInt = 0;

  /**
   * constructor
   */
  public ThingStuffData() {
  }
  
  public ThingStuffData(long thingId, long thingStuffId, 
      long thingStuffTypeId, String value, boolean valueBol, double valueDouble, long valueInt) {
    this.thingId = thingId;
    this.thingStuffId = thingStuffId;
    this.thingStuffTypeId = thingStuffTypeId;
    this.value = value;
    this.valueBol = valueBol;
    this.valueDouble = valueDouble;
    this.valueInt = valueInt;
  }
  
  public long getId() {
    return thingStuffId;
  }
  
  public void setId(long thingStuffId) {
    this.thingStuffId = thingStuffId;
  }
  
  public long getThingId() {
    return thingId;
  }
  
  public void setThingId(long thingId) {
    this.thingId = thingId;
  }
  
  public long getThingStuffTypeId() {
    return thingStuffTypeId;
  }
  
  public void setThingStuffTypeId(long thingStuffTypeId) {
    this.thingStuffTypeId = thingStuffTypeId;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public void setValue(boolean value) {
    this.valueBol = value;
  }
  
  public void setValue(double value) {
    this.valueDouble = value;
  }
  
  public String getValue() {
    return value;
  }
  
  public boolean getValueBol() {
    return valueBol;
  }
  
  public double getValueDouble() {
    return valueDouble;
  }
  
  public void setValueInt(long valueInt) {
    this.valueInt = valueInt;
  }
  
  public long getValueInt() {
    return valueInt;
  }
  
}
