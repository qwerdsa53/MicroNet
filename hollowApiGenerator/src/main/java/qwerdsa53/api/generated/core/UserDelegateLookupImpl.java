package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.objects.delegate.HollowObjectAbstractDelegate;
import com.netflix.hollow.core.read.dataaccess.HollowObjectTypeDataAccess;
import com.netflix.hollow.core.schema.HollowObjectSchema;

@SuppressWarnings("all")
public class UserDelegateLookupImpl extends HollowObjectAbstractDelegate implements UserDelegate {

    private final UserTypeAPI typeAPI;

    public UserDelegateLookupImpl(UserTypeAPI typeAPI) {
        this.typeAPI = typeAPI;
    }

    public long getId(int ordinal) {
        ordinal = typeAPI.getIdOrdinal(ordinal);
        return ordinal == -1 ? Long.MIN_VALUE : typeAPI.getAPI().getLongTypeAPI().getValue(ordinal);
    }

    public Long getIdBoxed(int ordinal) {
        ordinal = typeAPI.getIdOrdinal(ordinal);
        return ordinal == -1 ? null : typeAPI.getAPI().getLongTypeAPI().getValueBoxed(ordinal);
    }

    public int getIdOrdinal(int ordinal) {
        return typeAPI.getIdOrdinal(ordinal);
    }

    public String getUsername(int ordinal) {
        ordinal = typeAPI.getUsernameOrdinal(ordinal);
        return ordinal == -1 ? null : typeAPI.getAPI().getStringTypeAPI().getValue(ordinal);
    }

    public boolean isUsernameEqual(int ordinal, String testValue) {
        ordinal = typeAPI.getUsernameOrdinal(ordinal);
        return ordinal == -1 ? testValue == null : typeAPI.getAPI().getStringTypeAPI().isValueEqual(ordinal, testValue);
    }

    public int getUsernameOrdinal(int ordinal) {
        return typeAPI.getUsernameOrdinal(ordinal);
    }

    public String getPassword(int ordinal) {
        ordinal = typeAPI.getPasswordOrdinal(ordinal);
        return ordinal == -1 ? null : typeAPI.getAPI().getStringTypeAPI().getValue(ordinal);
    }

    public boolean isPasswordEqual(int ordinal, String testValue) {
        ordinal = typeAPI.getPasswordOrdinal(ordinal);
        return ordinal == -1 ? testValue == null : typeAPI.getAPI().getStringTypeAPI().isValueEqual(ordinal, testValue);
    }

    public int getPasswordOrdinal(int ordinal) {
        return typeAPI.getPasswordOrdinal(ordinal);
    }

    public String getEmail(int ordinal) {
        ordinal = typeAPI.getEmailOrdinal(ordinal);
        return ordinal == -1 ? null : typeAPI.getAPI().getStringTypeAPI().getValue(ordinal);
    }

    public boolean isEmailEqual(int ordinal, String testValue) {
        ordinal = typeAPI.getEmailOrdinal(ordinal);
        return ordinal == -1 ? testValue == null : typeAPI.getAPI().getStringTypeAPI().isValueEqual(ordinal, testValue);
    }

    public int getEmailOrdinal(int ordinal) {
        return typeAPI.getEmailOrdinal(ordinal);
    }

    public boolean getEnabled(int ordinal) {
        return typeAPI.getEnabled(ordinal);
    }

    public Boolean getEnabledBoxed(int ordinal) {
        return typeAPI.getEnabledBoxed(ordinal);
    }

    public int getFriendsOrdinal(int ordinal) {
        return typeAPI.getFriendsOrdinal(ordinal);
    }

    public int getUserBlackListOrdinal(int ordinal) {
        return typeAPI.getUserBlackListOrdinal(ordinal);
    }

    public int getFriendRequestsOrdinal(int ordinal) {
        return typeAPI.getFriendRequestsOrdinal(ordinal);
    }

    public String getProfilePictureUrl(int ordinal) {
        ordinal = typeAPI.getProfilePictureUrlOrdinal(ordinal);
        return ordinal == -1 ? null : typeAPI.getAPI().getStringTypeAPI().getValue(ordinal);
    }

    public boolean isProfilePictureUrlEqual(int ordinal, String testValue) {
        ordinal = typeAPI.getProfilePictureUrlOrdinal(ordinal);
        return ordinal == -1 ? testValue == null : typeAPI.getAPI().getStringTypeAPI().isValueEqual(ordinal, testValue);
    }

    public int getProfilePictureUrlOrdinal(int ordinal) {
        return typeAPI.getProfilePictureUrlOrdinal(ordinal);
    }

    public int getCreatedAtOrdinal(int ordinal) {
        return typeAPI.getCreatedAtOrdinal(ordinal);
    }

    public int getBirthdayOrdinal(int ordinal) {
        return typeAPI.getBirthdayOrdinal(ordinal);
    }

    public String getDescription(int ordinal) {
        ordinal = typeAPI.getDescriptionOrdinal(ordinal);
        return ordinal == -1 ? null : typeAPI.getAPI().getStringTypeAPI().getValue(ordinal);
    }

    public boolean isDescriptionEqual(int ordinal, String testValue) {
        ordinal = typeAPI.getDescriptionOrdinal(ordinal);
        return ordinal == -1 ? testValue == null : typeAPI.getAPI().getStringTypeAPI().isValueEqual(ordinal, testValue);
    }

    public int getDescriptionOrdinal(int ordinal) {
        return typeAPI.getDescriptionOrdinal(ordinal);
    }

    public int getLastSeenOrdinal(int ordinal) {
        return typeAPI.getLastSeenOrdinal(ordinal);
    }

    public int getUpdatedAtOrdinal(int ordinal) {
        return typeAPI.getUpdatedAtOrdinal(ordinal);
    }

    public UserTypeAPI getTypeAPI() {
        return typeAPI;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

}