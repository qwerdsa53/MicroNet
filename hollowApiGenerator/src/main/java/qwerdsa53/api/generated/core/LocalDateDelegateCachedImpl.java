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
public class LocalDateDelegateCachedImpl extends HollowObjectAbstractDelegate implements HollowCachedDelegate, LocalDateDelegate {

    private final Integer year;
    private final Integer month;
    private final Integer day;
    private LocalDateTypeAPI typeAPI;

    public LocalDateDelegateCachedImpl(LocalDateTypeAPI typeAPI, int ordinal) {
        this.year = typeAPI.getYearBoxed(ordinal);
        this.month = typeAPI.getMonthBoxed(ordinal);
        this.day = typeAPI.getDayBoxed(ordinal);
        this.typeAPI = typeAPI;
    }

    public int getYear(int ordinal) {
        if(year == null)
            return Integer.MIN_VALUE;
        return year.intValue();
    }

    public Integer getYearBoxed(int ordinal) {
        return year;
    }

    public int getMonth(int ordinal) {
        if(month == null)
            return Integer.MIN_VALUE;
        return month.intValue();
    }

    public Integer getMonthBoxed(int ordinal) {
        return month;
    }

    public int getDay(int ordinal) {
        if(day == null)
            return Integer.MIN_VALUE;
        return day.intValue();
    }

    public Integer getDayBoxed(int ordinal) {
        return day;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

    public LocalDateTypeAPI getTypeAPI() {
        return typeAPI;
    }

    public void updateTypeAPI(HollowTypeAPI typeAPI) {
        this.typeAPI = (LocalDateTypeAPI) typeAPI;
    }

}