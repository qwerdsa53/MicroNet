package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class LocalDateTimeDelegateLookupImpl extends HollowObjectAbstractDelegate implements LocalDateTimeDelegate {

    private final LocalDateTimeTypeAPI typeAPI;

    public LocalDateTimeDelegateLookupImpl(LocalDateTimeTypeAPI typeAPI) {
        this.typeAPI = typeAPI;
    }

    public int getDateOrdinal(int ordinal) {
        return typeAPI.getDateOrdinal(ordinal);
    }

    public int getTimeOrdinal(int ordinal) {
        return typeAPI.getTimeOrdinal(ordinal);
    }

    public LocalDateTimeTypeAPI getTypeAPI() {
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