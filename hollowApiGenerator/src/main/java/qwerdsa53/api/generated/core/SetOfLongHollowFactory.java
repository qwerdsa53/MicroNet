package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.objects.provider.HollowFactory;
import com.netflix.hollow.core.read.dataaccess.HollowTypeDataAccess;
import com.netflix.hollow.api.custom.HollowTypeAPI;
import com.netflix.hollow.api.objects.delegate.HollowSetCachedDelegate;

@SuppressWarnings("all")
public class SetOfLongHollowFactory<T extends SetOfLong> extends HollowFactory<T> {

    @Override
    public T newHollowObject(HollowTypeDataAccess dataAccess, HollowTypeAPI typeAPI, int ordinal) {
        return (T)new SetOfLong(((SetOfLongTypeAPI)typeAPI).getDelegateLookupImpl(), ordinal);
    }

    @Override
    public T newCachedHollowObject(HollowTypeDataAccess dataAccess, HollowTypeAPI typeAPI, int ordinal) {
        return (T)new SetOfLong(new HollowSetCachedDelegate((SetOfLongTypeAPI)typeAPI, ordinal), ordinal);
    }

}