package com.gonevertical.server.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import com.gonevertical.server.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
@Version(strategy = VersionStrategy.VERSION_NUMBER, extensions = { @Extension(vendorName = "datanucleus", key = "key", value = "version") })
public class WalletItemData {
  
  public static PersistenceManager getPersistenceManager() {
    return PMF.get().getPersistenceManager();
  }
  
  public static WalletItemData findWalletItemData(String id) {
    Long uid = UserData.getLoggedInUserId();
    if (id == null) {
      return null;
    }
    Key key = KeyFactory.stringToKey(id);
    PersistenceManager pm = getPersistenceManager();
    try {
      WalletItemData e = pm.getObjectById(WalletItemData.class, key);
      if (e.getUserId() != uid) {
        e = null;
      }
      return e;
    } finally {
      pm.close();
    }
  }
  
  public static long countAll() {
    PersistenceManager pm = getPersistenceManager();
    try {
      // TODO change to JDO
      //return ((Number) em.createQuery("select count(o) from WalletItemData o").getSingleResult()).longValue();
    } finally {
      pm.close();
    }
    return 0l;
  }
  
  public static long countWalletItemDataByUser() {
    Long uid = UserData.getLoggedInUserId();
    PersistenceManager pm = getPersistenceManager();
    try {
      javax.jdo.Query query = pm.newQuery("select count(o) from WalletItemData o where o.userId=:userId");
      // TODO change to JDO
      //query.setParameter("userId", uid);
      //return ((Number) query.getSingleResult()).longValue();
    } finally {
      pm.close();
    }
    return 0l;
  }
  
  public static List<WalletItemData> findWalletItemDataByUser() {
    Long uid = UserData.getLoggedInUserId();
    PersistenceManager pm = getPersistenceManager();
    try {
      javax.jdo.Query query = pm.newQuery("select o from WalletItemData o");
      query.setFilter("userId=\"" + uid + "\"");
      List<WalletItemData> list = (List<WalletItemData>) query.execute();
      list.size();
      return list;
    } finally {
      pm.close();
    }
  }
  
  
  
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;

  @Persistent
  private Long version;
  
  /**
   * the entity owner - the person who's logged in. Will set this on the client side. 
   * I'm not to concerned b/c its a child of a parent. Keep for reference in debugging.
   */
  @Persistent
  private Long userId;
  
  @Persistent
  private String name;
  
  @Persistent
  private String contact;
  
  
  public void setId(Key parentKey, String id) {
    if (parentKey == null || id == null) {
      return;
    }
    Key k = KeyFactory.stringToKey(id);
    this.key = KeyFactory.createKey(parentKey, WalletItemData.class.getName(), k.getId());
  }
  public void setId(String id) {
    key = KeyFactory.stringToKey(id);
  }
  public String getId() {
    String id = null;
    if (key != null) {
      id = KeyFactory.keyToString(key);
    }
    return id;
  }
  
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  public Long getUserId() {
    return userId;
  }
  
  public void setVersion(Long version) {
    this.version = version;
  }
  public Long getVersion() {
    return version;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
  
  public void setContact(String contact) {
    this.contact = contact;
  }
  public String getContact() {
    return contact;
  }
  
  
  public void persist() {
    // set the owner of this entity
    userId = UserData.getLoggedInUserId();
    
    // JPA does this automatically, but JDO won't. Not sure why.
    if (version == null) {
      version = 0l;
    }
    version++;
    
    PersistenceManager pm = getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      tx.begin();
      pm.makePersistent(this);
      tx.commit();
    } finally {
      pm.close();
    }
  }

  public void remove() {
    
    Key key = null; // TODO, configure parent key,...
    
    PersistenceManager pm = getPersistenceManager();
    Transaction tx = pm.currentTransaction();
    try {
      WalletData e = pm.getObjectById(WalletData.class, key);
      tx.begin();
      pm.deletePersistent(e);
      tx.commit();
    } finally {
      pm.close();
    }
  }
}
