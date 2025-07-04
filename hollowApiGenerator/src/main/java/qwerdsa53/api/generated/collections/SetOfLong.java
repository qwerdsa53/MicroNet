package qwerdsa53.api.generated.collections;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.UserAPI;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.objects.HollowSet;
import com.netflix.hollow.core.schema.HollowSetSchema;
import com.netflix.hollow.api.objects.delegate.HollowSetDelegate;
import com.netflix.hollow.api.objects.generic.GenericHollowRecordHelper;

@SuppressWarnings("all")
public class SetOfLong extends HollowSet<HLong> {

    public SetOfLong(HollowSetDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    @Override
    public HLong instantiateElement(int ordinal) {
        return (HLong) api().getHLong(ordinal);
    }

    @Override
    public boolean equalsElement(int elementOrdinal, Object testObject) {
        return GenericHollowRecordHelper.equalObject(getSchema().getElementType(), elementOrdinal, testObject);
    }

    public UserAPI api() {
        return typeApi().getAPI();
    }

    public SetOfLongTypeAPI typeApi() {
        return (SetOfLongTypeAPI) delegate.getTypeAPI();
    }

}