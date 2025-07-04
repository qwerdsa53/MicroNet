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
public class LocalDate extends HollowObject {

    public LocalDate(LocalDateDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    public int getYear() {
        return delegate().getYear(ordinal);
    }



    public int getMonth() {
        return delegate().getMonth(ordinal);
    }



    public int getDay() {
        return delegate().getDay(ordinal);
    }



    public UserAPI api() {
        return typeApi().getAPI();
    }

    public LocalDateTypeAPI typeApi() {
        return delegate().getTypeAPI();
    }

    protected LocalDateDelegate delegate() {
        return (LocalDateDelegate)delegate;
    }

    public String toString() {
        return new HollowRecordStringifier().stringify(this);
    }

}