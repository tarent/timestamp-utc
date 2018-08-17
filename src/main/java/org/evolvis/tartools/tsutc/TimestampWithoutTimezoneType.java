package org.evolvis.tartools.tsutc;

import org.hibernate.type.TimestampType;

/**
 * Hibernate type for persisting a (millis-only) java.util.Date
 * as SQL “TIMESTAMP” (aka “TIMESTAMP WITHOUT TIME ZONE”) in UTC.
 *
 * Sample use in an Entity:
 *
 * <pre><code>
 * {@literal @}Type(type="org.evolvis.tartools.tsutc.TimestampWithoutTimezoneType")
 * {@literal @}Column(name="update_date")
 * private Date updateDate;
 * </pre></code>
 *
 * @author mirabilos (t.glaser@tarent.de)
 */
public final class TimestampWithoutTimezoneType extends TimestampType {
    private static final long serialVersionUID = 1579882833495519695L;
    public static final TimestampWithoutTimezoneType INSTANCE = new TimestampWithoutTimezoneType();

    /**
     * Constructs the class and overrides the {@link TimestampType} parent’s
     * SQL type descriptor to {@link TimestampWithoutTimezoneTypeDescriptor}.
     */
    public TimestampWithoutTimezoneType() {
        super();
        setSqlTypeDescriptor(TimestampWithoutTimezoneTypeDescriptor.INSTANCE);
    }
}
