package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.UserAPI;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.client.HollowAPIFactory;
import com.netflix.hollow.api.custom.HollowAPI;
import com.netflix.hollow.api.objects.provider.HollowFactory;
import com.netflix.hollow.core.read.dataaccess.HollowDataAccess;
import java.util.Collections;
import java.util.Set;

@SuppressWarnings("all")
public class UserAPIFactory implements HollowAPIFactory {

    private final Set<String> cachedTypes;

    public UserAPIFactory() {
        this(Collections.<String>emptySet());
    }

    public UserAPIFactory(Set<String> cachedTypes) {
        this.cachedTypes = cachedTypes;
    }

    @Override
    public HollowAPI createAPI(HollowDataAccess dataAccess) {
        return new UserAPI(dataAccess, cachedTypes);
    }

    @Override
    public HollowAPI createAPI(HollowDataAccess dataAccess, HollowAPI previousCycleAPI) {
        if (!(previousCycleAPI instanceof UserAPI)) {
            throw new ClassCastException(previousCycleAPI.getClass() + " not instance of UserAPI");        }
        return new UserAPI(dataAccess, cachedTypes, Collections.<String, HollowFactory<?>>emptyMap(), (UserAPI) previousCycleAPI);
    }

}