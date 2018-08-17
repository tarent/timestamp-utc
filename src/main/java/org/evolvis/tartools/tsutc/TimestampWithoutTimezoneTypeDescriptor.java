package org.evolvis.tartools.tsutc;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.TimestampTypeDescriptor;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Descriptor for {@link Types#TIMESTAMP TIMESTAMP} handling.
 * Adapted for TIMESTAMP WITHOUT TIME ZONE as UTC by mirabilos.
 *
 * @author Steve Ebersole
 */
final class TimestampWithoutTimezoneTypeDescriptor extends TimestampTypeDescriptor {
    private static final long serialVersionUID = -4069550993182675748L;
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    public static final TimestampWithoutTimezoneTypeDescriptor INSTANCE = new TimestampWithoutTimezoneTypeDescriptor();

    /**
     * {@inheritDoc}
     */
    @Override
    public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>(javaTypeDescriptor, this) {
            @Override
            protected void doBind(final PreparedStatement st, final X value, final int index, final WrapperOptions options)
              throws SQLException {
                final Timestamp timestamp = javaTypeDescriptor.unwrap(value, Timestamp.class, options);
                if (value instanceof Calendar) {
                    st.setTimestamp(index, timestamp, (Calendar) value);
                } else {
                    st.setTimestamp(index, timestamp, Calendar.getInstance(UTC));
                }
            }

            @Override
            protected void doBind(final CallableStatement st, final X value, final String name, final WrapperOptions options)
              throws SQLException {
                final Timestamp timestamp = javaTypeDescriptor.unwrap(value, Timestamp.class, options);
                if (value instanceof Calendar) {
                    st.setTimestamp(name, timestamp, (Calendar) value);
                } else {
                    st.setTimestamp(name, timestamp, Calendar.getInstance(UTC));
                }
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>(javaTypeDescriptor, this) {
            @Override
            protected X doExtract(final ResultSet rs, final String name, final WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(rs.getTimestamp(name, Calendar.getInstance(UTC)), options);
            }

            @Override
            protected X doExtract(final CallableStatement statement, final int index, final WrapperOptions options)
              throws SQLException {
                return javaTypeDescriptor.wrap(statement.getTimestamp(index, Calendar.getInstance(UTC)), options);
            }

            @Override
            protected X doExtract(final CallableStatement statement, final String name, final WrapperOptions options)
              throws SQLException {
                return javaTypeDescriptor.wrap(statement.getTimestamp(name, Calendar.getInstance(UTC)), options);
            }
        };
    }
}
