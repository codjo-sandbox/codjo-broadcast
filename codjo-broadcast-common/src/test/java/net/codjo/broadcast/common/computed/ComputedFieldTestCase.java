package net.codjo.broadcast.common.computed;
import java.sql.Connection;
import java.sql.SQLException;
import junit.framework.TestCase;
import net.codjo.broadcast.common.ComputedContextAdapter;
import net.codjo.broadcast.common.Context;
import net.codjo.broadcast.common.Preferences;
import net.codjo.database.common.api.TransactionManager;
import net.codjo.database.common.api.structure.SqlTable;
import net.codjo.tokio.TokioFixture;
/**
 *
 */
public abstract class ComputedFieldTestCase<C extends ComputedField, P extends Preferences> extends TestCase {
    protected TokioFixture tokio = new TokioFixture(getClass());
    protected ComputedContextAdapter computedContext;
    protected C computedField = createComputedField();
    protected P preferences = createPreferences();
    protected Context context;


    protected abstract C createComputedField();


    protected abstract P createPreferences();


    protected abstract void createSelectionTable() throws SQLException;


    //TODO[Segolene][a valider] - methode modifi�e pour ajouter le transaction manager
    protected void assertCase(final String storyName) throws Exception {
        final Connection connection = tokio.getConnection();
        TransactionManager<Void> transactionManager = new TransactionManager<Void>(connection) {
            @Override
            protected Void runSql(Connection connection) throws Exception {
                createSelectionTable();
                createComputedTable();

                tokio.insertInputInDb(storyName);

                computedField.compute(computedContext, tokio.getConnection());

                tokio.assertAllOutputs(storyName);
                return null;
            }
        };

            transactionManager.run(connection);
    }


    //TODO[Segolene][a corriger] - surcharg� dans codjo-sample parce que numeric ne fonctionne pas sur oracle
    protected void createComputedTable() {
        tokio.getJdbcFixture().create(SqlTable.table(preferences.getComputedTableName()),
                                      "SELECTION_ID numeric(18) not null, "
                                      + computedField.getSqlDefinition() + " null ");
    }


    @Override
    protected void setUp() throws Exception {
        tokio.doSetUp();
        context = new Context();
        computedContext = new ComputedContextAdapter(preferences, context);
    }


    @Override
    protected void tearDown() throws Exception {
        tokio.doTearDown();
    }
}
