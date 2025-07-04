package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.UserAPI;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.custom.HollowObjectTypeAPI;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;

@SuppressWarnings("all")
public class LocalDateTimeTypeAPI extends HollowObjectTypeAPI {

    private final LocalDateTimeDelegateLookupImpl delegateLookupImpl;

    public LocalDateTimeTypeAPI(UserAPI api, HollowObjectTypeDataAccess typeDataAccess) {
        super(api, typeDataAccess, new String[] {
            "date",
            "time"
        });
        this.delegateLookupImpl = new LocalDateTimeDelegateLookupImpl(this);
    }

    public int getDateOrdinal(int ordinal) {
        if(fieldIndex[0] == -1)
            return missingDataHandler().handleReferencedOrdinal("LocalDateTime", ordinal, "date");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[0]);
    }

    public LocalDateTypeAPI getDateTypeAPI() {
        return getAPI().getLocalDateTypeAPI();
    }

    public int getTimeOrdinal(int ordinal) {
        if(fieldIndex[1] == -1)
            return missingDataHandler().handleReferencedOrdinal("LocalDateTime", ordinal, "time");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[1]);
    }

    public LocalTimeTypeAPI getTimeTypeAPI() {
        return getAPI().getLocalTimeTypeAPI();
    }

    public LocalDateTimeDelegateLookupImpl getDelegateLookupImpl() {
        return delegateLookupImpl;
    }

    @Override
    public UserAPI getAPI() {
        return (UserAPI) api;
    }

}