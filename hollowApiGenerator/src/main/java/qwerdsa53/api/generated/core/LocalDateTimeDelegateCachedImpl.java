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
public class LocalDateTimeDelegateCachedImpl extends HollowObjectAbstractDelegate implements HollowCachedDelegate, LocalDateTimeDelegate {

    private final int dateOrdinal;
    private final int timeOrdinal;
    private LocalDateTimeTypeAPI typeAPI;

    public LocalDateTimeDelegateCachedImpl(LocalDateTimeTypeAPI typeAPI, int ordinal) {
        this.dateOrdinal = typeAPI.getDateOrdinal(ordinal);
        this.timeOrdinal = typeAPI.getTimeOrdinal(ordinal);
        this.typeAPI = typeAPI;
    }

    public int getDateOrdinal(int ordinal) {
        return dateOrdinal;
    }

    public int getTimeOrdinal(int ordinal) {
        return timeOrdinal;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

    public LocalDateTimeTypeAPI getTypeAPI() {
        return typeAPI;
    }

    public void updateTypeAPI(HollowTypeAPI typeAPI) {
        this.typeAPI = (LocalDateTimeTypeAPI) typeAPI;
    }

}