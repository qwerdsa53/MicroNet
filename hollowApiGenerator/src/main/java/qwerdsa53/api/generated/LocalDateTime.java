package qwerdsa53.api.generated;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.UserAPI;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.objects.HollowObject;
import com.netflix.hollow.core.schema.HollowObjectSchema;
import com.netflix.hollow.tools.stringifier.HollowRecordStringifier;

@SuppressWarnings("all")
public class LocalDateTime extends HollowObject {

    public LocalDateTime(LocalDateTimeDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    public LocalDate getDate() {
        int refOrdinal = delegate().getDateOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getLocalDate(refOrdinal);
    }

    public LocalTime getTime() {
        int refOrdinal = delegate().getTimeOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getLocalTime(refOrdinal);
    }

    public UserAPI api() {
        return typeApi().getAPI();
    }

    public LocalDateTimeTypeAPI typeApi() {
        return delegate().getTypeAPI();
    }

    protected LocalDateTimeDelegate delegate() {
        return (LocalDateTimeDelegate)delegate;
    }

    public String toString() {
        return new HollowRecordStringifier().stringify(this);
    }

}