package com.gawkat.core.server.jdo;

import com.gawkat.core.client.SetDefaultsData;
import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.oauth.Sha1;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.data.ThingJdo;
import com.gawkat.core.server.jdo.data.ThingStuffTypeJdo;
import com.gawkat.core.server.jdo.data.ThingTypeJdo;

public class SetDefaults {

  private ServerPersistence sp = null;
  


  
  public SetDefaults(ServerPersistence sp) {
    this.sp = sp;
  }
  
  public boolean setDefaults(int defaultType) {
    
    if (defaultType == SetDefaultsData.DEFAULT_THING_TYPES) { // thing types
      setThingTypes();
    
    } else if (defaultType == SetDefaultsData.DEFAULT_THING_APPLICATIONS) { // thing
      createApplication();
    
    } else if (defaultType == SetDefaultsData.DEFAULT_THING_USERS) { // thing
      createUser();
      
    } else if (defaultType == SetDefaultsData.DEFAULT_THINGS) { // things
    	 createApplication();
    	 createUser();
    	 
    } else if (defaultType == SetDefaultsData.DEFAULT_THING_STUFF_TYPES) {
    	createStuffTypes();
    }
    
    return true;
  }
  
  private void createStuffTypes() {
  	
  	ThingStuffTypeData a = new ThingStuffTypeData();
  	a.setData(0, "Text", ThingStuffTypeData.VT_STRING);
  	
  	ThingStuffTypeData b = new ThingStuffTypeData();
  	b.setData(0, "CheckBox", ThingStuffTypeData.VT_BOOLEAN);
  	
  	ThingStuffTypeData c = new ThingStuffTypeData();
  	c.setData(0, "Decimal", ThingStuffTypeData.VT_DOUBLE);
  	
  	ThingStuffTypeData d = new ThingStuffTypeData();
  	d.setData(0, "Number", ThingStuffTypeData.VT_INT);

  	ThingStuffTypeData e = new ThingStuffTypeData();
  	e.setData(0, "Large Text Box", ThingStuffTypeData.VT_STRING_LARGE);
  	
  	ThingStuffTypeData f = new ThingStuffTypeData();
  	f.setData(0, "Text Case Sensitive", ThingStuffTypeData.VT_STRING_CASE);
  	
  	ThingStuffTypeData g = new ThingStuffTypeData();
  	g.setData(0, "Large Text Area Case Sensitive", ThingStuffTypeData.VT_STRING_LARGE_CASE);
  	
  	ThingStuffTypeData i = new ThingStuffTypeData();
  	i.setData(0, "HTML", ThingStuffTypeData.VT_HTML);
  	
  	ThingStuffTypeData j = new ThingStuffTypeData();
  	j.setData(0, "URL", ThingStuffTypeData.VT_URL);
  	
  	ThingStuffTypeData k = new ThingStuffTypeData();
  	k.setData(0, "Email", ThingStuffTypeData.VT_EMAIL);
  	
  	ThingStuffTypeData l = new ThingStuffTypeData();
  	l.setData(0, "Phone", ThingStuffTypeData.VT_PHONE);
  	
  	ThingStuffTypeData m = new ThingStuffTypeData();
  	m.setData(0, "Money", ThingStuffTypeData.VT_STRING);
  	
  	ThingStuffTypeJdo j1 = new ThingStuffTypeJdo();
  	j1.setData(a);
  	j1.insertUnique();
  	
  	ThingStuffTypeJdo j2 = new ThingStuffTypeJdo();
  	j2.setData(b);
  	j2.insertUnique();
  	
  	ThingStuffTypeJdo j3 = new ThingStuffTypeJdo();
  	j3.setData(c);
  	j3.insertUnique();
  	
  	ThingStuffTypeJdo j4 = new ThingStuffTypeJdo();
  	j4.setData(d);
  	j4.insertUnique();
  	
  	ThingStuffTypeJdo j5 = new ThingStuffTypeJdo();
  	j5.setData(e);
  	j5.insertUnique();
  	
  	ThingStuffTypeJdo j6 = new ThingStuffTypeJdo();
  	j6.setData(f);
  	j6.insertUnique();
  	
  	ThingStuffTypeJdo j7 = new ThingStuffTypeJdo();
  	j7.setData(g);
  	j7.insertUnique();
  	
  	ThingStuffTypeJdo j8 = new ThingStuffTypeJdo();
  	j8.setData(i);
  	j8.insertUnique();
  	
  	ThingStuffTypeJdo j9 = new ThingStuffTypeJdo();
  	j9.setData(j);
  	j9.insertUnique();
  	
  	ThingStuffTypeJdo j10 = new ThingStuffTypeJdo();
  	j10.setData(k);
  	j10.insertUnique();
  	
  	ThingStuffTypeJdo j11 = new ThingStuffTypeJdo();
  	j11.setData(l);
  	j11.insertUnique();
  	
  	ThingStuffTypeJdo j12 = new ThingStuffTypeJdo();
  	j12.setData(m);
  	j12.insertUnique();
  }

	/**
   * set default things
   */
  private void setThingTypes() {
    
    ServerPersistence sp = new ServerPersistence();
    
    ThingTypeData at = new ThingTypeData();
    at.setName("Application");
    
    ThingTypeData bt = new ThingTypeData();
    bt.setName("Person");
    
    ThingTypeData ct = new ThingTypeData();
    ct.setName("Group");
    
    ThingTypeData dt = new ThingTypeData();
    dt.setName("Widget");
    
    ThingTypeData et = new ThingTypeData();
    et.setName("Permission");
    
    ThingTypeData ft = new ThingTypeData();
    ft.setName("Thing Stuff Template");
    
    ThingTypeJdo a = new ThingTypeJdo();
    a.setData(at);
    a.insertUnique();
    
    ThingTypeJdo b = new ThingTypeJdo();
    b.setData(bt);
    b.insertUnique();
    
    ThingTypeJdo c = new ThingTypeJdo();
    c.setData(ct);
    c.insertUnique();
    
    ThingTypeJdo d = new ThingTypeJdo();
    d.setData(dt);
    d.insertUnique();
    
    ThingTypeJdo e = new ThingTypeJdo();
    e.setData(et);
    e.insertUnique();
    
    ThingTypeJdo f = new ThingTypeJdo();
    f.setData(ft);
    f.insertUnique();
    
  }
  
  /**
   * set default application
   */
  private void createApplication() {
    
    Sha1 sha = new Sha1();
    
    long thingTypeId = ThingTypeData.TYPE_APPLICATION;
    String key = "demo_application";
    String secret = sha.hex_hmac_sha1("salt", "password");
    
    ThingJdo a = new ThingJdo();
    a.insert(thingTypeId, key, secret);
    
  }
  
  /**
   * set a default user for testing
   */
  private void createUser() {
    Sha1 sha = new Sha1();
    
    long thingTypeId = ThingTypeData.TYPE_USER;
    String key = "demo_user";
    String password = "password";
    String secret = sha.b64_sha1(password);
    //String secret = sha.hex_hmac_sha1("salt", "password"); // would need to do this on the client side too.
    
    ThingJdo a = new ThingJdo();
    a.insert(thingTypeId, key, secret);
  }
  
  
}
