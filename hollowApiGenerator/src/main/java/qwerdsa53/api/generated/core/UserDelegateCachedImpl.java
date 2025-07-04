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
public class UserDelegateCachedImpl extends HollowObjectAbstractDelegate implements HollowCachedDelegate, UserDelegate {

    private final Long id;
    private final int idOrdinal;
    private final String username;
    private final int usernameOrdinal;
    private final String password;
    private final int passwordOrdinal;
    private final String email;
    private final int emailOrdinal;
    private final Boolean enabled;
    private final int friendsOrdinal;
    private final int userBlackListOrdinal;
    private final int friendRequestsOrdinal;
    private final String profilePictureUrl;
    private final int profilePictureUrlOrdinal;
    private final int createdAtOrdinal;
    private final int birthdayOrdinal;
    private final String description;
    private final int descriptionOrdinal;
    private final int lastSeenOrdinal;
    private final int updatedAtOrdinal;
    private UserTypeAPI typeAPI;

    public UserDelegateCachedImpl(UserTypeAPI typeAPI, int ordinal) {
        this.idOrdinal = typeAPI.getIdOrdinal(ordinal);
        int idTempOrdinal = idOrdinal;
        this.id = idTempOrdinal == -1 ? null : typeAPI.getAPI().getLongTypeAPI().getValue(idTempOrdinal);
        this.usernameOrdinal = typeAPI.getUsernameOrdinal(ordinal);
        int usernameTempOrdinal = usernameOrdinal;
        this.username = usernameTempOrdinal == -1 ? null : typeAPI.getAPI().getStringTypeAPI().getValue(usernameTempOrdinal);
        this.passwordOrdinal = typeAPI.getPasswordOrdinal(ordinal);
        int passwordTempOrdinal = passwordOrdinal;
        this.password = passwordTempOrdinal == -1 ? null : typeAPI.getAPI().getStringTypeAPI().getValue(passwordTempOrdinal);
        this.emailOrdinal = typeAPI.getEmailOrdinal(ordinal);
        int emailTempOrdinal = emailOrdinal;
        this.email = emailTempOrdinal == -1 ? null : typeAPI.getAPI().getStringTypeAPI().getValue(emailTempOrdinal);
        this.enabled = typeAPI.getEnabledBoxed(ordinal);
        this.friendsOrdinal = typeAPI.getFriendsOrdinal(ordinal);
        this.userBlackListOrdinal = typeAPI.getUserBlackListOrdinal(ordinal);
        this.friendRequestsOrdinal = typeAPI.getFriendRequestsOrdinal(ordinal);
        this.profilePictureUrlOrdinal = typeAPI.getProfilePictureUrlOrdinal(ordinal);
        int profilePictureUrlTempOrdinal = profilePictureUrlOrdinal;
        this.profilePictureUrl = profilePictureUrlTempOrdinal == -1 ? null : typeAPI.getAPI().getStringTypeAPI().getValue(profilePictureUrlTempOrdinal);
        this.createdAtOrdinal = typeAPI.getCreatedAtOrdinal(ordinal);
        this.birthdayOrdinal = typeAPI.getBirthdayOrdinal(ordinal);
        this.descriptionOrdinal = typeAPI.getDescriptionOrdinal(ordinal);
        int descriptionTempOrdinal = descriptionOrdinal;
        this.description = descriptionTempOrdinal == -1 ? null : typeAPI.getAPI().getStringTypeAPI().getValue(descriptionTempOrdinal);
        this.lastSeenOrdinal = typeAPI.getLastSeenOrdinal(ordinal);
        this.updatedAtOrdinal = typeAPI.getUpdatedAtOrdinal(ordinal);
        this.typeAPI = typeAPI;
    }

    public long getId(int ordinal) {
        if(id == null)
            return Long.MIN_VALUE;
        return id.longValue();
    }

    public Long getIdBoxed(int ordinal) {
        return id;
    }

    public int getIdOrdinal(int ordinal) {
        return idOrdinal;
    }

    public String getUsername(int ordinal) {
        return username;
    }

    public boolean isUsernameEqual(int ordinal, String testValue) {
        if(testValue == null)
            return username == null;
        return testValue.equals(username);
    }

    public int getUsernameOrdinal(int ordinal) {
        return usernameOrdinal;
    }

    public String getPassword(int ordinal) {
        return password;
    }

    public boolean isPasswordEqual(int ordinal, String testValue) {
        if(testValue == null)
            return password == null;
        return testValue.equals(password);
    }

    public int getPasswordOrdinal(int ordinal) {
        return passwordOrdinal;
    }

    public String getEmail(int ordinal) {
        return email;
    }

    public boolean isEmailEqual(int ordinal, String testValue) {
        if(testValue == null)
            return email == null;
        return testValue.equals(email);
    }

    public int getEmailOrdinal(int ordinal) {
        return emailOrdinal;
    }

    public boolean getEnabled(int ordinal) {
        if(enabled == null)
            return false;
        return enabled.booleanValue();
    }

    public Boolean getEnabledBoxed(int ordinal) {
        return enabled;
    }

    public int getFriendsOrdinal(int ordinal) {
        return friendsOrdinal;
    }

    public int getUserBlackListOrdinal(int ordinal) {
        return userBlackListOrdinal;
    }

    public int getFriendRequestsOrdinal(int ordinal) {
        return friendRequestsOrdinal;
    }

    public String getProfilePictureUrl(int ordinal) {
        return profilePictureUrl;
    }

    public boolean isProfilePictureUrlEqual(int ordinal, String testValue) {
        if(testValue == null)
            return profilePictureUrl == null;
        return testValue.equals(profilePictureUrl);
    }

    public int getProfilePictureUrlOrdinal(int ordinal) {
        return profilePictureUrlOrdinal;
    }

    public int getCreatedAtOrdinal(int ordinal) {
        return createdAtOrdinal;
    }

    public int getBirthdayOrdinal(int ordinal) {
        return birthdayOrdinal;
    }

    public String getDescription(int ordinal) {
        return description;
    }

    public boolean isDescriptionEqual(int ordinal, String testValue) {
        if(testValue == null)
            return description == null;
        return testValue.equals(description);
    }

    public int getDescriptionOrdinal(int ordinal) {
        return descriptionOrdinal;
    }

    public int getLastSeenOrdinal(int ordinal) {
        return lastSeenOrdinal;
    }

    public int getUpdatedAtOrdinal(int ordinal) {
        return updatedAtOrdinal;
    }

    @Override
    public HollowObjectSchema getSchema() {
        return typeAPI.getTypeDataAccess().getSchema();
    }

    @Override
    public HollowObjectTypeDataAccess getTypeDataAccess() {
        return typeAPI.getTypeDataAccess();
    }

    public UserTypeAPI getTypeAPI() {
        return typeAPI;
    }

    public void updateTypeAPI(HollowTypeAPI typeAPI) {
        this.typeAPI = (UserTypeAPI) typeAPI;
    }

}