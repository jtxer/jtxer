package org.jtxer.test.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.jtxer.annotations.Transaction;
import org.jtxer.core.JtxerHelper;
import org.jtxer.core.TransactionStatus;
import org.jtxer.support.jdbc.JdbcSession;

/**
 * @author yuqs
 */
public class Service1 {
	@Transaction(readOnly = false)
	public void execute(Service2 service2) {
		TransactionStatus t = JtxerHelper.getTxManager().getTransaction();
		JdbcSession session = t.requestResource(JdbcSession.class);
		try {
			System.out.println(session);
			Connection conn = session.getConnection();
			System.out.println(conn);
			conn.prepareStatement("insert into tbl_test (name) values ('aa')").executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		service2.execute();
	}
}
