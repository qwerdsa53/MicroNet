package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.objects.delegate.HollowObjectDelegate;


@SuppressWarnings("all")
public interface LocalDateDelegate extends HollowObjectDelegate {

    public int getYear(int ordinal);

    public Integer getYearBoxed(int ordinal);

    public int getMonth(int ordinal);

    public Integer getMonthBoxed(int ordinal);

    public int getDay(int ordinal);

    public Integer getDayBoxed(int ordinal);

    public LocalDateTypeAPI getTypeAPI();

}