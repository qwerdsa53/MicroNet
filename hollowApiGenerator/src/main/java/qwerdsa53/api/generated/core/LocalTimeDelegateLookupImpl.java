package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class LocalTimeDelegateLookupImpl extends HollowObjectAbstractDelegate implements LocalTimeDelegate {

    private final LocalTimeTypeAPI typeAPI;

    public LocalTimeDelegateLookupImpl(LocalTimeTypeAPI typeAPI) {
        this.typeAPI = typeAPI;
    }

    public int getHour(int ordinal) {
        return typeAPI.getHour(ordinal);
    }

    public Integer getHourBoxed(int ordinal) {
        return typeAPI.getHourBoxed(ordinal);
    }

    public int getMinute(int ordinal) {
        return typeAPI.getMinute(ordinal);
    }

    public Integer getMinuteBoxed(int ordinal) {
        return typeAPI.getMinuteBoxed(ordinal);
    }

    public int getSecond(int ordinal) {
        return typeAPI.getSecond(ordinal);
    }

    public Integer getSecondBoxed(int ordinal) {
        return typeAPI.getSecondBoxed(ordinal);
    }

    public int getNano(int ordinal) {
        return typeAPI.getNano(ordinal);
    }

    public Integer getNanoBoxed(int ordinal) {
        return typeAPI.getNanoBoxed(ordinal);
    }

    public LocalTimeTypeAPI getTypeAPI() {
        return typeAPI;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

}