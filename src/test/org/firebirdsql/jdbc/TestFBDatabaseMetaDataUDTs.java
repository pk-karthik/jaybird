/*
 * Firebird Open Source JavaEE Connector - JDBC Driver
 *
 * Distributable under LGPL license.
 * You may obtain a copy of the License at http://www.gnu.org/copyleft/lgpl.html
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * LGPL License for more details.
 *
 * This file was created by members of the firebird development team.
 * All individual contributions remain the Copyright (C) of those
 * individuals.  Contributors to this file are either listed here or
 * can be obtained from a source control history command.
 *
 * All rights reserved.
 */
package org.firebirdsql.jdbc;

import org.firebirdsql.jdbc.MetaDataValidator.MetaDataInfo;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Tests for {@link FBDatabaseMetaData} for UDT related metadata.
 * 
 * @author <a href="mailto:mrotteveel@users.sourceforge.net">Mark Rotteveel</a>
 */
public class TestFBDatabaseMetaDataUDTs extends FBMetaDataTestBase<TestFBDatabaseMetaDataUDTs.UDTMetaData> {

    public TestFBDatabaseMetaDataUDTs() {
        super(UDTMetaData.class);
    }

    @Override
    protected List<String> getCreateStatements() {
        return Collections.emptyList();
    }

    @Override
    protected Map<UDTMetaData, Object> getDefaultValueValidationRules() throws Exception {
        return null;
    }
    
    /**
     * Tests the ordinal positions and types for the metadata columns of getUDTs().
     */
    @Test
    public void testUDTMetaDataColumns() throws Exception {
        try (ResultSet udts = dbmd.getUDTs(null, null, null, null)) {
            validateResultSetColumns(udts);
        }
    }
    
    // As Firebird does not support UDTs no other tests are necessary
    
    /**
     * Columns defined for the getUDTs() metadata.
     */
    enum UDTMetaData implements MetaDataInfo {
        TYPE_CAT(1, String.class),
        TYPE_SCHEM(2, String.class),
        TYPE_NAME(3, String.class),
        CLASS_NAME(4, String.class),
        DATA_TYPE(5, Integer.class),
        REMARKS(6, String.class),
        BASE_TYPE(7, Short.class)
        ;

        private final int position;
        private final Class<?> columnClass;

        UDTMetaData(int position, Class<?> columnClass) {
            this.position = position;
            this.columnClass = columnClass;
        }

        public int getPosition() {
            return position;
        }

        public Class<?> getColumnClass() {
            return columnClass;
        }

        public MetaDataValidator<?> getValidator() {
            return new MetaDataValidator<>(this);
        }
        
    }
}
