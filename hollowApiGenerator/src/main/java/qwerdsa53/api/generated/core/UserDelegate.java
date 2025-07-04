package qwerdsa53.api.generated.core;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.objects.delegate.HollowObjectDelegate;


@SuppressWarnings("all")
public interface UserDelegate extends HollowObjectDelegate {

    public long getId(int ordinal);

    public Long getIdBoxed(int ordinal);

    public int getIdOrdinal(int ordinal);

    public String getUsername(int ordinal);

    public boolean isUsernameEqual(int ordinal, String testValue);

    public int getUsernameOrdinal(int ordinal);

    public String getPassword(int ordinal);

    public boolean isPasswordEqual(int ordinal, String testValue);

    public int getPasswordOrdinal(int ordinal);

    public String getEmail(int ordinal);

    public boolean isEmailEqual(int ordinal, String testValue);

    public int getEmailOrdinal(int ordinal);

    public boolean getEnabled(int ordinal);

    public Boolean getEnabledBoxed(int ordinal);

    public int getFriendsOrdinal(int ordinal);

    public int getUserBlackListOrdinal(int ordinal);

    public int getFriendRequestsOrdinal(int ordinal);

    public String getProfilePictureUrl(int ordinal);

    public boolean isProfilePictureUrlEqual(int ordinal, String testValue);

    public int getProfilePictureUrlOrdinal(int ordinal);

    public int getCreatedAtOrdinal(int ordinal);

    public int getBirthdayOrdinal(int ordinal);

    public String getDescription(int ordinal);

    public boolean isDescriptionEqual(int ordinal, String testValue);

    public int getDescriptionOrdinal(int ordinal);

    public int getLastSeenOrdinal(int ordinal);

    public int getUpdatedAtOrdinal(int ordinal);

    public UserTypeAPI getTypeAPI();

}