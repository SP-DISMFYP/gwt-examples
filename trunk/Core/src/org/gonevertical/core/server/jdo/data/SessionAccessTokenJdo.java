package org.gonevertical.core.server.jdo.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.gonevertical.core.client.ui.admin.thing.ThingData;
import org.gonevertical.core.server.ServerPersistence;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class SessionAccessTokenJdo {

	@NotPersistent
	private static final Logger log = Logger.getLogger(SessionAccessTokenJdo.class.getName());
	
	@NotPersistent
	private ServerPersistence sp;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key idKey;

	@Persistent
	private long thingTypeId;

	@Persistent
	private long thingId;

	@Persistent
	private String accessToken;

	@Persistent
	private String accessTokenSecret;

	@Persistent
	private Date dateCreated;

	@Persistent
	private Date dateUpdated;

	public SessionAccessTokenJdo(ServerPersistence sp) {
		this.sp = sp;
	}

	public void set(ServerPersistence sp) {
		this.sp = sp;
	}
	
	/**
	 * insert access token
	 * 
	 * @param thingTypeId
	 * @param thingId
	 * @param accessToken
	 * @param accessTokenSecret
	 */
	public boolean insert(Long thingTypeId, Long thingId, String accessToken, String accessTokenSecret) {
		this.thingTypeId = thingTypeId;
		this.thingId = thingId;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
		this.dateCreated = new Date();

		boolean success = false;
		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.makePersistent(this);
			tx.commit();
			success = true;
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "", e);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return success;
	}

	public long getId() {
		return idKey.getId();
	}

	public void setThingId(long thingId) {
		this.thingId = thingId;
	}

	public void setThingTypeId(long thingTypeId) {
		this.thingTypeId = thingTypeId;
	}

	public long getThingId() {
		return thingId;
	}

	public long getThingTypeId() {
		return thingTypeId;
	}

	/**
	 * query accessToken - this is probably overkill for what I need, only need one object at a time most of the time
	 * 
	 * @param accessToken
	 * @param accessTokenSecret
	 * @return
	 */
	public SessionAccessTokenJdo[] query(String accessToken, String accessTokenSecret) {

		String qfilter = "accessToken==\"" + accessToken + "\" && accessTokenSecret==\"" + accessTokenSecret + "\" ";

		SessionAccessTokenJdo[] r = null;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Extent<SessionAccessTokenJdo> e = pm.getExtent(SessionAccessTokenJdo.class, true);
			Query q = pm.newQuery(e, qfilter);
			Collection<SessionAccessTokenJdo> c = (Collection<SessionAccessTokenJdo>) q.execute();
			if (c.size() > 0) {
				r = new SessionAccessTokenJdo[c.size()];
				c.toArray(r);
			}
			q.closeAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}

		return r;
	}

	/**
	 * get the owner of the session
	 * 
	 * @param accessToken
	 * @param accessTokenSecret
	 * @return
	 */
	public ThingData getThingData(String accessToken, String accessTokenSecret) {
		if (accessToken == null || accessTokenSecret == null) {
			return null;
		}
		SessionAccessTokenJdo[] s = query(accessToken, accessTokenSecret);
		ThingData t = new ThingData(s[0].getThingTypeId(), s[0].getThingId());
		return t;
	}

	/**
	 * update Access Token and change the owner to userId;
	 * @param id
	 * @param userId
	 * @return
	 */
	public boolean updateAccessToken(long id, long userId) {

		boolean success = false;

		PersistenceManager pm = sp.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			
			SessionAccessTokenJdo sa = null;
			sa = pm.getObjectById(SessionAccessTokenJdo.class, id);
			sa.set(sp);
			sa.setThingId(userId);
			sa.setThingTypeId(ThingTypeJdo.TYPE_USER); // switch session var to the user and not the application
			
			pm.makePersistent(sa);
			
			success = true;
			
			tx.commit();
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "", e);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return success;
	}
	
	@SuppressWarnings("unchecked")
  public long queryTotal() {
		long total = 0;
		PersistenceManager pm = sp.getPersistenceManager();
		try {
			Query q = pm.newQuery("select idKey from " + SessionAccessTokenJdo.class.getName());
	    List<Key> ids = (List<Key>) q.execute();
			total = ids.size();
			q.closeAll();
		} catch (Exception e) { 
			e.printStackTrace();
			log.log(Level.SEVERE, "", e);
		} finally {
			pm.close();
		}
		return total;
	}

	public String toString() {
		String s = "";
		s += "idKey=" + idKey + " ";
		s += "thingTypeId=" + thingTypeId + " ";
		s += "thingId=" + thingId + " ";
		s += "accessToken=" + accessToken + " ";
		s += "accessTokenSecret=" + accessTokenSecret + " ";
		s += "dateCreated=" + dateCreated + " ";
		s += "dateUpdated" + dateUpdated + " ";
		return s;
	}












}