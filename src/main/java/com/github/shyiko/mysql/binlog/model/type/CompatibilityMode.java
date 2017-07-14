package com.github.shyiko.mysql.binlog.model.type;

public enum CompatibilityMode {
        /**
         * Return DATETIME/DATETIME_V2/TIMESTAMP/TIMESTAMP_V2/DATE/TIME/TIME_V2 values as long|s
         * (number of milliseconds since the epoch (00:00:00 Coordinated Universal Time (UTC), Thursday, 1 January 1970,
         * not counting leap seconds)) (instead of java.util.Date/java.sql.Timestamp/java.sql.Date/new java.sql.Time).
         *
         * <p>This option is going to be enabled by default starting from mysql-binlog-connector-java@1.0.0.
         */
        DATE_AND_TIME_AS_LONG,

        /* Same as {@link CompatibilityMode#DATE_AND_TIME_AS_LONG} but values are returned in microseconds. */
        DATE_AND_TIME_AS_LONG_MICRO,

        /**
         * Return CHAR/VARCHAR/BINARY/VARBINARY values as byte[]|s (instead of String|s).
         *
         * <p>This option is going to be enabled by default starting from mysql-binlog-connector-java@1.0.0.
         */
        CHAR_AND_BINARY_AS_BYTE_ARRAY
}
