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
public class LocalTime extends HollowObject {

    public LocalTime(LocalTimeDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    public int getHour() {
        return delegate().getHour(ordinal);
    }



    public int getMinute() {
        return delegate().getMinute(ordinal);
    }



    public int getSecond() {
        return delegate().getSecond(ordinal);
    }



    public int getNano() {
        return delegate().getNano(ordinal);
    }



    public UserAPI api() {
        return typeApi().getAPI();
    }

    public LocalTimeTypeAPI typeApi() {
        return delegate().getTypeAPI();
    }

    protected LocalTimeDelegate delegate() {
        return (LocalTimeDelegate)delegate;
    }

    public String toString() {
        return new HollowRecordStringifier().stringify(this);
    }

}