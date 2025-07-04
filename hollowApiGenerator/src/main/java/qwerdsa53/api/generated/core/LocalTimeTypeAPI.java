package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.UserAPI;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.custom.HollowObjectTypeAPI;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;

@SuppressWarnings("all")
public class LocalTimeTypeAPI extends HollowObjectTypeAPI {

    private final LocalTimeDelegateLookupImpl delegateLookupImpl;

    public LocalTimeTypeAPI(UserAPI api, HollowObjectTypeDataAccess typeDataAccess) {
        super(api, typeDataAccess, new String[] {
            "hour",
            "minute",
            "second",
            "nano"
        });
        this.delegateLookupImpl = new LocalTimeDelegateLookupImpl(this);
    }

    public int getHour(int ordinal) {
        if(fieldIndex[0] == -1)
            return missingDataHandler().handleInt("LocalTime", ordinal, "hour");
        return getTypeDataAccess().readInt(ordinal, fieldIndex[0]);
    }

    public Integer getHourBoxed(int ordinal) {
        int i;
        if(fieldIndex[0] == -1) {
            i = missingDataHandler().handleInt("LocalTime", ordinal, "hour");
        } else {
            boxedFieldAccessSampler.recordFieldAccess(fieldIndex[0]);
            i = getTypeDataAccess().readInt(ordinal, fieldIndex[0]);
        }
        if(i == Integer.MIN_VALUE)
            return null;
        return Integer.valueOf(i);
    }



    public int getMinute(int ordinal) {
        if(fieldIndex[1] == -1)
            return missingDataHandler().handleInt("LocalTime", ordinal, "minute");
        return getTypeDataAccess().readInt(ordinal, fieldIndex[1]);
    }

    public Integer getMinuteBoxed(int ordinal) {
        int i;
        if(fieldIndex[1] == -1) {
            i = missingDataHandler().handleInt("LocalTime", ordinal, "minute");
        } else {
            boxedFieldAccessSampler.recordFieldAccess(fieldIndex[1]);
            i = getTypeDataAccess().readInt(ordinal, fieldIndex[1]);
        }
        if(i == Integer.MIN_VALUE)
            return null;
        return Integer.valueOf(i);
    }



    public int getSecond(int ordinal) {
        if(fieldIndex[2] == -1)
            return missingDataHandler().handleInt("LocalTime", ordinal, "second");
        return getTypeDataAccess().readInt(ordinal, fieldIndex[2]);
    }

    public Integer getSecondBoxed(int ordinal) {
        int i;
        if(fieldIndex[2] == -1) {
            i = missingDataHandler().handleInt("LocalTime", ordinal, "second");
        } else {
            boxedFieldAccessSampler.recordFieldAccess(fieldIndex[2]);
            i = getTypeDataAccess().readInt(ordinal, fieldIndex[2]);
        }
        if(i == Integer.MIN_VALUE)
            return null;
        return Integer.valueOf(i);
    }



    public int getNano(int ordinal) {
        if(fieldIndex[3] == -1)
            return missingDataHandler().handleInt("LocalTime", ordinal, "nano");
        return getTypeDataAccess().readInt(ordinal, fieldIndex[3]);
    }

    public Integer getNanoBoxed(int ordinal) {
        int i;
        if(fieldIndex[3] == -1) {
            i = missingDataHandler().handleInt("LocalTime", ordinal, "nano");
        } else {
            boxedFieldAccessSampler.recordFieldAccess(fieldIndex[3]);
            i = getTypeDataAccess().readInt(ordinal, fieldIndex[3]);
        }
        if(i == Integer.MIN_VALUE)
            return null;
        return Integer.valueOf(i);
    }



    public LocalTimeDelegateLookupImpl getDelegateLookupImpl() {
        return delegateLookupImpl;
    }

    @Override
    public UserAPI getAPI() {
        return (UserAPI) api;
    }

}