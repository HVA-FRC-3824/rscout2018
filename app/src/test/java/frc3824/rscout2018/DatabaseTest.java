package frc3824.rscout2018;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the functions within the Database and the Data Models
 */

public class DatabaseTest
{
    @Test
    public void testDataModel()
    {
        DataModelTest dmt = new DataModelTest();

        assertEquals(dmt.isDirty(), false);

        dmt.setTestChar('c');

        assertEquals(dmt.isDirty(), true);

        dmt.sync();
    }
}
