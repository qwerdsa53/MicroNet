package qwerdsa53.api.generated.accessor;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.UserAPI;
import qwerdsa53.api.generated.LocalTime;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.data.AbstractHollowDataAccessor;
import com.netflix.hollow.core.index.key.PrimaryKey;
import com.netflix.hollow.core.read.engine.HollowReadStateEngine;

@SuppressWarnings("all")
public class LocalTimeDataAccessor extends AbstractHollowDataAccessor<LocalTime> {

    public static final String TYPE = "LocalTime";
    private UserAPI api;

    public LocalTimeDataAccessor(HollowConsumer consumer) {
        super(consumer, TYPE);
        this.api = (UserAPI)consumer.getAPI();
    }

    public LocalTimeDataAccessor(HollowReadStateEngine rStateEngine, UserAPI api) {
        super(rStateEngine, TYPE);
        this.api = api;
    }

    public LocalTimeDataAccessor(HollowReadStateEngine rStateEngine, UserAPI api, String ... fieldPaths) {
        super(rStateEngine, TYPE, fieldPaths);
        this.api = api;
    }

    public LocalTimeDataAccessor(HollowReadStateEngine rStateEngine, UserAPI api, PrimaryKey primaryKey) {
        super(rStateEngine, TYPE, primaryKey);
        this.api = api;
    }

    @Override public LocalTime getRecord(int ordinal){
        return api.getLocalTime(ordinal);
    }

}