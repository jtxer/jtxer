package org.jtxer.test.service;

import java.sql.Connection;
import java.sql.SQLException;

import org.jtxer.annotations.Transaction;
import org.jtxer.core.JtxerHelper;
import org.jtxer.core.Propagation;
import org.jtxer.core.TransactionStatus;
import org.jtxer.support.jdbc.JdbcSession;

/**
 * @author yuqs
 */
public class Service2 {
	@Transaction(readOnly = false, propagation = Propagation.PROPAGATION_REQUIRED)
	public void execute() {
		TransactionStatus t = JtxerHelper.getTxManager().getTransaction();
		JdbcSession session = t.requestResource(JdbcSession.class);
		try {
			System.out.println(session);
			Connection conn = session.getConnection();
			System.out.println(conn);
			conn.prepareStatement("insert into tbl_test (name) values ('bb')").executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
