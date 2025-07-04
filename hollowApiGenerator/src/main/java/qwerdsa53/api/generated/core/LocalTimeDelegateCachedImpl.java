package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;
import com.netflix.hollow.api.custom.HollowTypeAPI;
import com.netflix.hollow.api.objects.delegate.HollowCachedDelegate;

@SuppressWarnings("all")
public class LocalTimeDelegateCachedImpl extends HollowObjectAbstractDelegate implements HollowCachedDelegate, LocalTimeDelegate {

    private final Integer hour;
    private final Integer minute;
    private final Integer second;
    private final Integer nano;
    private LocalTimeTypeAPI typeAPI;

    public LocalTimeDelegateCachedImpl(LocalTimeTypeAPI typeAPI, int ordinal) {
        this.hour = typeAPI.getHourBoxed(ordinal);
        this.minute = typeAPI.getMinuteBoxed(ordinal);
        this.second = typeAPI.getSecondBoxed(ordinal);
        this.nano = typeAPI.getNanoBoxed(ordinal);
        this.typeAPI = typeAPI;
    }

    public int getHour(int ordinal) {
        if(hour == null)
            return Integer.MIN_VALUE;
        return hour.intValue();
    }

    public Integer getHourBoxed(int ordinal) {
        return hour;
    }

    public int getMinute(int ordinal) {
        if(minute == null)
            return Integer.MIN_VALUE;
        return minute.intValue();
    }

    public Integer getMinuteBoxed(int ordinal) {
        return minute;
    }

    public int getSecond(int ordinal) {
        if(second == null)
            return Integer.MIN_VALUE;
        return second.intValue();
    }

    public Integer getSecondBoxed(int ordinal) {
        return second;
    }

    public int getNano(int ordinal) {
        if(nano == null)
            return Integer.MIN_VALUE;
        return nano.intValue();
    }

    public Integer getNanoBoxed(int ordinal) {
        return nano;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

    public LocalTimeTypeAPI getTypeAPI() {
        return typeAPI;
    }

    public void updateTypeAPI(HollowTypeAPI typeAPI) {
        this.typeAPI = (LocalTimeTypeAPI) typeAPI;
    }

}