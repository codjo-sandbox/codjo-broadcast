/*
 * codjo.net
 *
 * Common Apache License 2.0
 */
package net.codjo.broadcast.common;
import net.codjo.test.common.mock.ConnectionMock;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
public class ConnectionProviderMock implements ConnectionProvider {
    static final String MOCK_APPLICATION_ACCOUNT = "APP_USER";


    public Connection getConnection() {
        return customizeConnectionMock().getStub();
    }


    public void releaseConnection(Connection connection) {
    }


    private static ConnectionMock customizeConnectionMock() {
        return new ConnectionMock() {
            @Override
            public DatabaseMetaData getMetaData() throws SQLException {
                DatabaseMetaData metaData = mock(DatabaseMetaData.class);
                when(metaData.getUserName()).thenReturn(MOCK_APPLICATION_ACCOUNT);
                return metaData;
            }
        };
    }
}
