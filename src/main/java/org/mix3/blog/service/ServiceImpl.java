package org.mix3.blog.service;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.java.ao.EntityManager;
import net.java.ao.db.C3P0PoolProvider;

import org.mix3.blog.util.H2DatabaseProvider;
import org.mix3.blog.util.Utils;

import com.google.inject.Singleton;

@Singleton
public class ServiceImpl implements Service{
	@SuppressWarnings("unused")
	private EntityManager em;
	
	public ServiceImpl(){
		Properties dbProperties = Utils.getDBProperties();
		String uri = dbProperties.getProperty("db.uri");
		String username = dbProperties.getProperty("db.username");
		String password = dbProperties.getProperty("db.password");
		
		em = new EntityManager(
				new C3P0PoolProvider(
						new H2DatabaseProvider(uri, username, password)));
		Logger.getLogger("net.java.ao").setLevel(Level.FINE);
	}
}
