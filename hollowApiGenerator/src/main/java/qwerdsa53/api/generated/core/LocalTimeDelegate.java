package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.objects.delegate.HollowObjectDelegate;


@SuppressWarnings("all")
public interface LocalTimeDelegate extends HollowObjectDelegate {

    public int getHour(int ordinal);

    public Integer getHourBoxed(int ordinal);

    public int getMinute(int ordinal);

    public Integer getMinuteBoxed(int ordinal);

    public int getSecond(int ordinal);

    public Integer getSecondBoxed(int ordinal);

    public int getNano(int ordinal);

    public Integer getNanoBoxed(int ordinal);

    public LocalTimeTypeAPI getTypeAPI();

}