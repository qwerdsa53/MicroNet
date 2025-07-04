package qwerdsa53.api.generated.index;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.UserAPI;
import qwerdsa53.api.generated.LocalDateTime;
import qwerdsa53.api.generated.User;
import qwerdsa53.api.generated.LocalTime;
import qwerdsa53.api.generated.LocalDate;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.core.index.HollowHashIndexResult;
import java.util.Collections;
import java.lang.Iterable;
import com.netflix.hollow.api.consumer.index.AbstractHollowHashIndex;
import com.netflix.hollow.api.consumer.data.AbstractHollowOrdinalIterable;


/**
 * @deprecated see {@link com.netflix.hollow.api.consumer.index.HashIndex} which can be built as follows:
 * <pre>{@code
 *     HashIndex<LocalDate, K> uki = HashIndex.from(consumer, LocalDate.class)
 *         .usingBean(k);
 *     Stream<LocalDate> results = uki.findMatches(k);
 * }</pre>
 * where {@code K} is a class declaring key field paths members, annotated with
 * {@link com.netflix.hollow.api.consumer.index.FieldPath}, and {@code k} is an instance of
 * {@code K} that is the query to find the matching {@code LocalDate} objects.
 */
@Deprecated
@SuppressWarnings("all")
public class UserAPIHashIndex extends AbstractHollowHashIndex<UserAPI> {

    public UserAPIHashIndex(HollowConsumer consumer, String queryType, String selectFieldPath, String... matchFieldPaths) {
        super(consumer, false, queryType, selectFieldPath, matchFieldPaths);
    }

    public UserAPIHashIndex(HollowConsumer consumer, boolean isListenToDataRefresh, String queryType, String selectFieldPath, String... matchFieldPaths) {
        super(consumer, isListenToDataRefresh, queryType, selectFieldPath, matchFieldPaths);
    }

    public Iterable<LocalDate> findLocalDateMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<LocalDate>(matches.iterator()) {
            public LocalDate getData(int ordinal) {
                return api.getLocalDate(ordinal);
            }
        };
    }

    public Iterable<LocalTime> findLocalTimeMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<LocalTime>(matches.iterator()) {
            public LocalTime getData(int ordinal) {
                return api.getLocalTime(ordinal);
            }
        };
    }

    public Iterable<LocalDateTime> findLocalDateTimeMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<LocalDateTime>(matches.iterator()) {
            public LocalDateTime getData(int ordinal) {
                return api.getLocalDateTime(ordinal);
            }
        };
    }

    public Iterable<HLong> findLongMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<HLong>(matches.iterator()) {
            public HLong getData(int ordinal) {
                return api.getHLong(ordinal);
            }
        };
    }

    public Iterable<SetOfLong> findSetOfLongMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<SetOfLong>(matches.iterator()) {
            public SetOfLong getData(int ordinal) {
                return api.getSetOfLong(ordinal);
            }
        };
    }

    public Iterable<HString> findStringMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<HString>(matches.iterator()) {
            public HString getData(int ordinal) {
                return api.getHString(ordinal);
            }
        };
    }

    public Iterable<User> findUserMatches(Object... keys) {
        HollowHashIndexResult matches = idx.findMatches(keys);
        if(matches == null) return Collections.emptySet();

        return new AbstractHollowOrdinalIterable<User>(matches.iterator()) {
            public User getData(int ordinal) {
                return api.getUser(ordinal);
            }
        };
    }

}