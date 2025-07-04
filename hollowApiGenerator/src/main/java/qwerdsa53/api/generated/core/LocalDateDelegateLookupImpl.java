package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class LocalDateDelegateLookupImpl extends HollowObjectAbstractDelegate implements LocalDateDelegate {

    private final LocalDateTypeAPI typeAPI;

    public LocalDateDelegateLookupImpl(LocalDateTypeAPI typeAPI) {
        this.typeAPI = typeAPI;
    }

    public int getYear(int ordinal) {
        return typeAPI.getYear(ordinal);
    }

    public Integer getYearBoxed(int ordinal) {
        return typeAPI.getYearBoxed(ordinal);
    }

    public int getMonth(int ordinal) {
        return typeAPI.getMonth(ordinal);
    }

    public Integer getMonthBoxed(int ordinal) {
        return typeAPI.getMonthBoxed(ordinal);
    }

    public int getDay(int ordinal) {
        return typeAPI.getDay(ordinal);
    }

    public Integer getDayBoxed(int ordinal) {
        return typeAPI.getDayBoxed(ordinal);
    }

    public LocalDateTypeAPI getTypeAPI() {
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