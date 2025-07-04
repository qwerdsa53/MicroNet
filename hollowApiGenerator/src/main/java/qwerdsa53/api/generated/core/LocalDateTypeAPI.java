package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.UserAPI;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.custom.HollowObjectTypeAPI;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;

@SuppressWarnings("all")
public class LocalDateTypeAPI extends HollowObjectTypeAPI {

    private final LocalDateDelegateLookupImpl delegateLookupImpl;

    public LocalDateTypeAPI(UserAPI api, HollowObjectTypeDataAccess typeDataAccess) {
        super(api, typeDataAccess, new String[] {
            "year",
            "month",
            "day"
        });
        this.delegateLookupImpl = new LocalDateDelegateLookupImpl(this);
    }

    public int getYear(int ordinal) {
        if(fieldIndex[0] == -1)
            return missingDataHandler().handleInt("LocalDate", ordinal, "year");
        return getTypeDataAccess().readInt(ordinal, fieldIndex[0]);
    }

    public Integer getYearBoxed(int ordinal) {
        int i;
        if(fieldIndex[0] == -1) {
            i = missingDataHandler().handleInt("LocalDate", ordinal, "year");
        } else {
            boxedFieldAccessSampler.recordFieldAccess(fieldIndex[0]);
            i = getTypeDataAccess().readInt(ordinal, fieldIndex[0]);
        }
        if(i == Integer.MIN_VALUE)
            return null;
        return Integer.valueOf(i);
    }



    public int getMonth(int ordinal) {
        if(fieldIndex[1] == -1)
            return missingDataHandler().handleInt("LocalDate", ordinal, "month");
        return getTypeDataAccess().readInt(ordinal, fieldIndex[1]);
    }

    public Integer getMonthBoxed(int ordinal) {
        int i;
        if(fieldIndex[1] == -1) {
            i = missingDataHandler().handleInt("LocalDate", ordinal, "month");
        } else {
            boxedFieldAccessSampler.recordFieldAccess(fieldIndex[1]);
            i = getTypeDataAccess().readInt(ordinal, fieldIndex[1]);
        }
        if(i == Integer.MIN_VALUE)
            return null;
        return Integer.valueOf(i);
    }



    public int getDay(int ordinal) {
        if(fieldIndex[2] == -1)
            return missingDataHandler().handleInt("LocalDate", ordinal, "day");
        return getTypeDataAccess().readInt(ordinal, fieldIndex[2]);
    }

    public Integer getDayBoxed(int ordinal) {
        int i;
        if(fieldIndex[2] == -1) {
            i = missingDataHandler().handleInt("LocalDate", ordinal, "day");
        } else {
            boxedFieldAccessSampler.recordFieldAccess(fieldIndex[2]);
            i = getTypeDataAccess().readInt(ordinal, fieldIndex[2]);
        }
        if(i == Integer.MIN_VALUE)
            return null;
        return Integer.valueOf(i);
    }



    public LocalDateDelegateLookupImpl getDelegateLookupImpl() {
        return delegateLookupImpl;
    }

    @Override
    public UserAPI getAPI() {
        return (UserAPI) api;
    }

}