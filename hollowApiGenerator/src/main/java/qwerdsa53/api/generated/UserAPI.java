package qwerdsa53.api.generated;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import java.util.Objects;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.Map;
import com.netflix.hollow.api.consumer.HollowConsumerAPI;
import com.netflix.hollow.api.custom.HollowAPI;
import com.netflix.hollow.core.read.dataaccess.HollowDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowListTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowSetTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.HollowMapTypeDataAccess;
import com.netflix.hollow.core.read.dataaccess.missing.HollowObjectMissingDataAccess;
import com.netflix.hollow.core.read.dataaccess.missing.HollowListMissingDataAccess;
import com.netflix.hollow.core.read.dataaccess.missing.HollowSetMissingDataAccess;
import com.netflix.hollow.core.read.dataaccess.missing.HollowMapMissingDataAccess;
import com.netflix.hollow.api.objects.provider.HollowFactory;
import com.netflix.hollow.api.objects.provider.HollowObjectProvider;
import com.netflix.hollow.api.objects.provider.HollowObjectCacheProvider;
import com.netflix.hollow.api.objects.provider.HollowObjectFactoryProvider;
import com.netflix.hollow.api.sampling.HollowObjectCreationSampler;
import com.netflix.hollow.api.sampling.HollowSamplingDirector;
import com.netflix.hollow.api.sampling.SampleResult;
import com.netflix.hollow.core.util.AllHollowRecordCollection;

@SuppressWarnings("all")
public class UserAPI extends HollowAPI implements  HollowConsumerAPI.LongRetriever, HollowConsumerAPI.StringRetriever {

    private final HollowObjectCreationSampler objectCreationSampler;

    private final LocalDateTypeAPI localDateTypeAPI;
    private final LocalTimeTypeAPI localTimeTypeAPI;
    private final LocalDateTimeTypeAPI localDateTimeTypeAPI;
    private final LongTypeAPI longTypeAPI;
    private final SetOfLongTypeAPI setOfLongTypeAPI;
    private final StringTypeAPI stringTypeAPI;
    private final UserTypeAPI userTypeAPI;

    private final HollowObjectProvider localDateProvider;
    private final HollowObjectProvider localTimeProvider;
    private final HollowObjectProvider localDateTimeProvider;
    private final HollowObjectProvider longProvider;
    private final HollowObjectProvider setOfLongProvider;
    private final HollowObjectProvider stringProvider;
    private final HollowObjectProvider userProvider;

    public UserAPI(HollowDataAccess dataAccess) {
        this(dataAccess, Collections.<String>emptySet());
    }

    public UserAPI(HollowDataAccess dataAccess, Set<String> cachedTypes) {
        this(dataAccess, cachedTypes, Collections.<String, HollowFactory<?>>emptyMap());
    }

    public UserAPI(HollowDataAccess dataAccess, Set<String> cachedTypes, Map<String, HollowFactory<?>> factoryOverrides) {
        this(dataAccess, cachedTypes, factoryOverrides, null);
    }

    public UserAPI(HollowDataAccess dataAccess, Set<String> cachedTypes, Map<String, HollowFactory<?>> factoryOverrides, UserAPI previousCycleAPI) {
        super(dataAccess);
        HollowTypeDataAccess typeDataAccess;
        HollowFactory factory;

        objectCreationSampler = new HollowObjectCreationSampler("LocalDate","LocalTime","LocalDateTime","Long","SetOfLong","String","User");

        typeDataAccess = dataAccess.getTypeDataAccess("LocalDate");
        if(typeDataAccess != null) {
            localDateTypeAPI = new LocalDateTypeAPI(this, (HollowObjectTypeDataAccess)typeDataAccess);
        } else {
            localDateTypeAPI = new LocalDateTypeAPI(this, new HollowObjectMissingDataAccess(dataAccess, "LocalDate"));
        }
        addTypeAPI(localDateTypeAPI);
        factory = factoryOverrides.get("LocalDate");
        if(factory == null)
            factory = new LocalDateHollowFactory();
        if(cachedTypes.contains("LocalDate")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.localDateProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.localDateProvider;
            localDateProvider = new HollowObjectCacheProvider(typeDataAccess, localDateTypeAPI, factory, previousCacheProvider);
        } else {
            localDateProvider = new HollowObjectFactoryProvider(typeDataAccess, localDateTypeAPI, factory);
        }

        typeDataAccess = dataAccess.getTypeDataAccess("LocalTime");
        if(typeDataAccess != null) {
            localTimeTypeAPI = new LocalTimeTypeAPI(this, (HollowObjectTypeDataAccess)typeDataAccess);
        } else {
            localTimeTypeAPI = new LocalTimeTypeAPI(this, new HollowObjectMissingDataAccess(dataAccess, "LocalTime"));
        }
        addTypeAPI(localTimeTypeAPI);
        factory = factoryOverrides.get("LocalTime");
        if(factory == null)
            factory = new LocalTimeHollowFactory();
        if(cachedTypes.contains("LocalTime")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.localTimeProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.localTimeProvider;
            localTimeProvider = new HollowObjectCacheProvider(typeDataAccess, localTimeTypeAPI, factory, previousCacheProvider);
        } else {
            localTimeProvider = new HollowObjectFactoryProvider(typeDataAccess, localTimeTypeAPI, factory);
        }

        typeDataAccess = dataAccess.getTypeDataAccess("LocalDateTime");
        if(typeDataAccess != null) {
            localDateTimeTypeAPI = new LocalDateTimeTypeAPI(this, (HollowObjectTypeDataAccess)typeDataAccess);
        } else {
            localDateTimeTypeAPI = new LocalDateTimeTypeAPI(this, new HollowObjectMissingDataAccess(dataAccess, "LocalDateTime"));
        }
        addTypeAPI(localDateTimeTypeAPI);
        factory = factoryOverrides.get("LocalDateTime");
        if(factory == null)
            factory = new LocalDateTimeHollowFactory();
        if(cachedTypes.contains("LocalDateTime")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.localDateTimeProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.localDateTimeProvider;
            localDateTimeProvider = new HollowObjectCacheProvider(typeDataAccess, localDateTimeTypeAPI, factory, previousCacheProvider);
        } else {
            localDateTimeProvider = new HollowObjectFactoryProvider(typeDataAccess, localDateTimeTypeAPI, factory);
        }

        typeDataAccess = dataAccess.getTypeDataAccess("Long");
        if(typeDataAccess != null) {
            longTypeAPI = new LongTypeAPI(this, (HollowObjectTypeDataAccess)typeDataAccess);
        } else {
            longTypeAPI = new LongTypeAPI(this, new HollowObjectMissingDataAccess(dataAccess, "Long"));
        }
        addTypeAPI(longTypeAPI);
        factory = factoryOverrides.get("Long");
        if(factory == null)
            factory = new LongHollowFactory();
        if(cachedTypes.contains("Long")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.longProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.longProvider;
            longProvider = new HollowObjectCacheProvider(typeDataAccess, longTypeAPI, factory, previousCacheProvider);
        } else {
            longProvider = new HollowObjectFactoryProvider(typeDataAccess, longTypeAPI, factory);
        }

        typeDataAccess = dataAccess.getTypeDataAccess("SetOfLong");
        if(typeDataAccess != null) {
            setOfLongTypeAPI = new SetOfLongTypeAPI(this, (HollowSetTypeDataAccess)typeDataAccess);
        } else {
            setOfLongTypeAPI = new SetOfLongTypeAPI(this, new HollowSetMissingDataAccess(dataAccess, "SetOfLong"));
        }
        addTypeAPI(setOfLongTypeAPI);
        factory = factoryOverrides.get("SetOfLong");
        if(factory == null)
            factory = new SetOfLongHollowFactory();
        if(cachedTypes.contains("SetOfLong")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.setOfLongProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.setOfLongProvider;
            setOfLongProvider = new HollowObjectCacheProvider(typeDataAccess, setOfLongTypeAPI, factory, previousCacheProvider);
        } else {
            setOfLongProvider = new HollowObjectFactoryProvider(typeDataAccess, setOfLongTypeAPI, factory);
        }

        typeDataAccess = dataAccess.getTypeDataAccess("String");
        if(typeDataAccess != null) {
            stringTypeAPI = new StringTypeAPI(this, (HollowObjectTypeDataAccess)typeDataAccess);
        } else {
            stringTypeAPI = new StringTypeAPI(this, new HollowObjectMissingDataAccess(dataAccess, "String"));
        }
        addTypeAPI(stringTypeAPI);
        factory = factoryOverrides.get("String");
        if(factory == null)
            factory = new StringHollowFactory();
        if(cachedTypes.contains("String")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.stringProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.stringProvider;
            stringProvider = new HollowObjectCacheProvider(typeDataAccess, stringTypeAPI, factory, previousCacheProvider);
        } else {
            stringProvider = new HollowObjectFactoryProvider(typeDataAccess, stringTypeAPI, factory);
        }

        typeDataAccess = dataAccess.getTypeDataAccess("User");
        if(typeDataAccess != null) {
            userTypeAPI = new UserTypeAPI(this, (HollowObjectTypeDataAccess)typeDataAccess);
        } else {
            userTypeAPI = new UserTypeAPI(this, new HollowObjectMissingDataAccess(dataAccess, "User"));
        }
        addTypeAPI(userTypeAPI);
        factory = factoryOverrides.get("User");
        if(factory == null)
            factory = new UserHollowFactory();
        if(cachedTypes.contains("User")) {
            HollowObjectCacheProvider previousCacheProvider = null;
            if(previousCycleAPI != null && (previousCycleAPI.userProvider instanceof HollowObjectCacheProvider))
                previousCacheProvider = (HollowObjectCacheProvider) previousCycleAPI.userProvider;
            userProvider = new HollowObjectCacheProvider(typeDataAccess, userTypeAPI, factory, previousCacheProvider);
        } else {
            userProvider = new HollowObjectFactoryProvider(typeDataAccess, userTypeAPI, factory);
        }

    }

    public void detachCaches() {
        if(localDateProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)localDateProvider).detach();
        if(localTimeProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)localTimeProvider).detach();
        if(localDateTimeProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)localDateTimeProvider).detach();
        if(longProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)longProvider).detach();
        if(setOfLongProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)setOfLongProvider).detach();
        if(stringProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)stringProvider).detach();
        if(userProvider instanceof HollowObjectCacheProvider)
            ((HollowObjectCacheProvider)userProvider).detach();
    }

    public LocalDateTypeAPI getLocalDateTypeAPI() {
        return localDateTypeAPI;
    }
    public LocalTimeTypeAPI getLocalTimeTypeAPI() {
        return localTimeTypeAPI;
    }
    public LocalDateTimeTypeAPI getLocalDateTimeTypeAPI() {
        return localDateTimeTypeAPI;
    }
    public LongTypeAPI getLongTypeAPI() {
        return longTypeAPI;
    }
    public SetOfLongTypeAPI getSetOfLongTypeAPI() {
        return setOfLongTypeAPI;
    }
    public StringTypeAPI getStringTypeAPI() {
        return stringTypeAPI;
    }
    public UserTypeAPI getUserTypeAPI() {
        return userTypeAPI;
    }
    public Collection<LocalDate> getAllLocalDate() {
        HollowTypeDataAccess tda = Objects.requireNonNull(getDataAccess().getTypeDataAccess("LocalDate"), "type not loaded or does not exist in dataset; type=LocalDate");
        return new AllHollowRecordCollection<LocalDate>(tda.getTypeState()) {
            protected LocalDate getForOrdinal(int ordinal) {
                return getLocalDate(ordinal);
            }
        };
    }
    public LocalDate getLocalDate(int ordinal) {
        objectCreationSampler.recordCreation(0);
        return (LocalDate)localDateProvider.getHollowObject(ordinal);
    }
    public Collection<LocalTime> getAllLocalTime() {
        HollowTypeDataAccess tda = Objects.requireNonNull(getDataAccess().getTypeDataAccess("LocalTime"), "type not loaded or does not exist in dataset; type=LocalTime");
        return new AllHollowRecordCollection<LocalTime>(tda.getTypeState()) {
            protected LocalTime getForOrdinal(int ordinal) {
                return getLocalTime(ordinal);
            }
        };
    }
    public LocalTime getLocalTime(int ordinal) {
        objectCreationSampler.recordCreation(1);
        return (LocalTime)localTimeProvider.getHollowObject(ordinal);
    }
    public Collection<LocalDateTime> getAllLocalDateTime() {
        HollowTypeDataAccess tda = Objects.requireNonNull(getDataAccess().getTypeDataAccess("LocalDateTime"), "type not loaded or does not exist in dataset; type=LocalDateTime");
        return new AllHollowRecordCollection<LocalDateTime>(tda.getTypeState()) {
            protected LocalDateTime getForOrdinal(int ordinal) {
                return getLocalDateTime(ordinal);
            }
        };
    }
    public LocalDateTime getLocalDateTime(int ordinal) {
        objectCreationSampler.recordCreation(2);
        return (LocalDateTime)localDateTimeProvider.getHollowObject(ordinal);
    }
    public Collection<HLong> getAllHLong() {
        HollowTypeDataAccess tda = Objects.requireNonNull(getDataAccess().getTypeDataAccess("Long"), "type not loaded or does not exist in dataset; type=Long");
        return new AllHollowRecordCollection<HLong>(tda.getTypeState()) {
            protected HLong getForOrdinal(int ordinal) {
                return getHLong(ordinal);
            }
        };
    }
    public HLong getHLong(int ordinal) {
        objectCreationSampler.recordCreation(3);
        return (HLong)longProvider.getHollowObject(ordinal);
    }
    public Collection<SetOfLong> getAllSetOfLong() {
        HollowTypeDataAccess tda = Objects.requireNonNull(getDataAccess().getTypeDataAccess("SetOfLong"), "type not loaded or does not exist in dataset; type=SetOfLong");
        return new AllHollowRecordCollection<SetOfLong>(tda.getTypeState()) {
            protected SetOfLong getForOrdinal(int ordinal) {
                return getSetOfLong(ordinal);
            }
        };
    }
    public SetOfLong getSetOfLong(int ordinal) {
        objectCreationSampler.recordCreation(4);
        return (SetOfLong)setOfLongProvider.getHollowObject(ordinal);
    }
    public Collection<HString> getAllHString() {
        HollowTypeDataAccess tda = Objects.requireNonNull(getDataAccess().getTypeDataAccess("String"), "type not loaded or does not exist in dataset; type=String");
        return new AllHollowRecordCollection<HString>(tda.getTypeState()) {
            protected HString getForOrdinal(int ordinal) {
                return getHString(ordinal);
            }
        };
    }
    public HString getHString(int ordinal) {
        objectCreationSampler.recordCreation(5);
        return (HString)stringProvider.getHollowObject(ordinal);
    }
    public Collection<User> getAllUser() {
        HollowTypeDataAccess tda = Objects.requireNonNull(getDataAccess().getTypeDataAccess("User"), "type not loaded or does not exist in dataset; type=User");
        return new AllHollowRecordCollection<User>(tda.getTypeState()) {
            protected User getForOrdinal(int ordinal) {
                return getUser(ordinal);
            }
        };
    }
    public User getUser(int ordinal) {
        objectCreationSampler.recordCreation(6);
        return (User)userProvider.getHollowObject(ordinal);
    }
    public void setSamplingDirector(HollowSamplingDirector director) {
        super.setSamplingDirector(director);
        objectCreationSampler.setSamplingDirector(director);
    }

    public Collection<SampleResult> getObjectCreationSamplingResults() {
        return objectCreationSampler.getSampleResults();
    }

}
