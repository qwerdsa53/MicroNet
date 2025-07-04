package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.UserAPI;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.custom.HollowObjectTypeAPI;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;

@SuppressWarnings("all")
public class UserTypeAPI extends HollowObjectTypeAPI {

    private final UserDelegateLookupImpl delegateLookupImpl;

    public UserTypeAPI(UserAPI api, HollowObjectTypeDataAccess typeDataAccess) {
        super(api, typeDataAccess, new String[] {
            "id",
            "username",
            "password",
            "email",
            "enabled",
            "friends",
            "userBlackList",
            "friendRequests",
            "profilePictureUrl",
            "createdAt",
            "birthday",
            "description",
            "lastSeen",
            "updatedAt"
        });
        this.delegateLookupImpl = new UserDelegateLookupImpl(this);
    }

    public int getIdOrdinal(int ordinal) {
        if(fieldIndex[0] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "id");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[0]);
    }

    public LongTypeAPI getIdTypeAPI() {
        return getAPI().getLongTypeAPI();
    }

    public int getUsernameOrdinal(int ordinal) {
        if(fieldIndex[1] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "username");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[1]);
    }

    public StringTypeAPI getUsernameTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getPasswordOrdinal(int ordinal) {
        if(fieldIndex[2] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "password");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[2]);
    }

    public StringTypeAPI getPasswordTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getEmailOrdinal(int ordinal) {
        if(fieldIndex[3] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "email");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[3]);
    }

    public StringTypeAPI getEmailTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public boolean getEnabled(int ordinal) {
        if(fieldIndex[4] == -1)
            return Boolean.TRUE.equals(missingDataHandler().handleBoolean("User", ordinal, "enabled"));
        return Boolean.TRUE.equals(getTypeDataAccess().readBoolean(ordinal, fieldIndex[4]));
    }

    public Boolean getEnabledBoxed(int ordinal) {
        if(fieldIndex[4] == -1)
            return missingDataHandler().handleBoolean("User", ordinal, "enabled");
        return getTypeDataAccess().readBoolean(ordinal, fieldIndex[4]);
    }



    public int getFriendsOrdinal(int ordinal) {
        if(fieldIndex[5] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "friends");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[5]);
    }

    public SetOfLongTypeAPI getFriendsTypeAPI() {
        return getAPI().getSetOfLongTypeAPI();
    }

    public int getUserBlackListOrdinal(int ordinal) {
        if(fieldIndex[6] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "userBlackList");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[6]);
    }

    public SetOfLongTypeAPI getUserBlackListTypeAPI() {
        return getAPI().getSetOfLongTypeAPI();
    }

    public int getFriendRequestsOrdinal(int ordinal) {
        if(fieldIndex[7] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "friendRequests");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[7]);
    }

    public SetOfLongTypeAPI getFriendRequestsTypeAPI() {
        return getAPI().getSetOfLongTypeAPI();
    }

    public int getProfilePictureUrlOrdinal(int ordinal) {
        if(fieldIndex[8] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "profilePictureUrl");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[8]);
    }

    public StringTypeAPI getProfilePictureUrlTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getCreatedAtOrdinal(int ordinal) {
        if(fieldIndex[9] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "createdAt");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[9]);
    }

    public LocalDateTimeTypeAPI getCreatedAtTypeAPI() {
        return getAPI().getLocalDateTimeTypeAPI();
    }

    public int getBirthdayOrdinal(int ordinal) {
        if(fieldIndex[10] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "birthday");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[10]);
    }

    public LocalDateTypeAPI getBirthdayTypeAPI() {
        return getAPI().getLocalDateTypeAPI();
    }

    public int getDescriptionOrdinal(int ordinal) {
        if(fieldIndex[11] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "description");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[11]);
    }

    public StringTypeAPI getDescriptionTypeAPI() {
        return getAPI().getStringTypeAPI();
    }

    public int getLastSeenOrdinal(int ordinal) {
        if(fieldIndex[12] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "lastSeen");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[12]);
    }

    public LocalDateTimeTypeAPI getLastSeenTypeAPI() {
        return getAPI().getLocalDateTimeTypeAPI();
    }

    public int getUpdatedAtOrdinal(int ordinal) {
        if(fieldIndex[13] == -1)
            return missingDataHandler().handleReferencedOrdinal("User", ordinal, "updatedAt");
        return getTypeDataAccess().readOrdinal(ordinal, fieldIndex[13]);
    }

    public LocalDateTimeTypeAPI getUpdatedAtTypeAPI() {
        return getAPI().getLocalDateTimeTypeAPI();
    }

    public UserDelegateLookupImpl getDelegateLookupImpl() {
        return delegateLookupImpl;
    }

    @Override
    public UserAPI getAPI() {
        return (UserAPI) api;
    }

}