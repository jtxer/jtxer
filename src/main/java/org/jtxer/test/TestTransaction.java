package org.jtxer.test;

import org.apache.commons.dbcp.BasicDataSource;
import org.jtxer.core.ResourceManager;
import org.jtxer.core.TransactionStatus;
import org.jtxer.core.TransactionDefinition;
import org.jtxer.core.TransactionManager;

import org.jtxer.support.jdbc.JdbcResourceManager;
import org.jtxer.support.jdbc.JdbcSession;

/**
 * @author yuqs
 */
public class TestTransaction {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// [1] create jtx manager and register our resource manager
	    TransactionManager manager = new TransactionManager();
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3406/jtxer");
		ds.setUsername("root");
		ds.setPassword("root");
		ResourceManager<JdbcSession> rm = new JdbcResourceManager(ds);
	    manager.registerResourceManager(rm);

	    // [2] request jtx
	    TransactionStatus status = manager.requestTransaction(new TransactionDefinition().propagationRequired());
	   
	    // [3] requrest resource i.e. start jtx
	    JdbcSession session = status.requestResource(JdbcSession.class);
	    
	    // [4] work
	    session.getConnection().prepareStatement("insert into tbl_test (name) values ('aa')").executeUpdate();
	    
	    // [5] commit
	    status.commit();
	    
	    // [6] cleanup
	    manager.close();
	}

}
