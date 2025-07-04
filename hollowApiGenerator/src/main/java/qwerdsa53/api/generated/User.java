package qwerdsa53.api.generated;

import com.netflix.hollow.core.type.*;
import qwerdsa53.api.generated.UserAPI;
import qwerdsa53.api.generated.core.*;
import qwerdsa53.api.generated.collections.*;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.objects.HollowObject;
import com.netflix.hollow.core.schema.HollowObjectSchema;
import com.netflix.hollow.tools.stringifier.HollowRecordStringifier;

@SuppressWarnings("all")
public class User extends HollowObject {

    public User(UserDelegate delegate, int ordinal) {
        super(delegate, ordinal);
    }

    public Long getId() {
        return delegate().getIdBoxed(ordinal);
    }

    public HLong getIdHollowReference() {
        int refOrdinal = delegate().getIdOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHLong(refOrdinal);
    }

    public String getUsername() {
        return delegate().getUsername(ordinal);
    }

    public boolean isUsernameEqual(String testValue) {
        return delegate().isUsernameEqual(ordinal, testValue);
    }

    public HString getUsernameHollowReference() {
        int refOrdinal = delegate().getUsernameOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public String getPassword() {
        return delegate().getPassword(ordinal);
    }

    public boolean isPasswordEqual(String testValue) {
        return delegate().isPasswordEqual(ordinal, testValue);
    }

    public HString getPasswordHollowReference() {
        int refOrdinal = delegate().getPasswordOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public String getEmail() {
        return delegate().getEmail(ordinal);
    }

    public boolean isEmailEqual(String testValue) {
        return delegate().isEmailEqual(ordinal, testValue);
    }

    public HString getEmailHollowReference() {
        int refOrdinal = delegate().getEmailOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public boolean getEnabled() {
        return delegate().getEnabled(ordinal);
    }



    public SetOfLong getFriends() {
        int refOrdinal = delegate().getFriendsOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getSetOfLong(refOrdinal);
    }

    public SetOfLong getUserBlackList() {
        int refOrdinal = delegate().getUserBlackListOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getSetOfLong(refOrdinal);
    }

    public SetOfLong getFriendRequests() {
        int refOrdinal = delegate().getFriendRequestsOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getSetOfLong(refOrdinal);
    }

    public String getProfilePictureUrl() {
        return delegate().getProfilePictureUrl(ordinal);
    }

    public boolean isProfilePictureUrlEqual(String testValue) {
        return delegate().isProfilePictureUrlEqual(ordinal, testValue);
    }

    public HString getProfilePictureUrlHollowReference() {
        int refOrdinal = delegate().getProfilePictureUrlOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public LocalDateTime getCreatedAt() {
        int refOrdinal = delegate().getCreatedAtOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getLocalDateTime(refOrdinal);
    }

    public LocalDate getBirthday() {
        int refOrdinal = delegate().getBirthdayOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getLocalDate(refOrdinal);
    }

    public String getDescription() {
        return delegate().getDescription(ordinal);
    }

    public boolean isDescriptionEqual(String testValue) {
        return delegate().isDescriptionEqual(ordinal, testValue);
    }

    public HString getDescriptionHollowReference() {
        int refOrdinal = delegate().getDescriptionOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getHString(refOrdinal);
    }

    public LocalDateTime getLastSeen() {
        int refOrdinal = delegate().getLastSeenOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getLocalDateTime(refOrdinal);
    }

    public LocalDateTime getUpdatedAt() {
        int refOrdinal = delegate().getUpdatedAtOrdinal(ordinal);
        if(refOrdinal == -1)
            return null;
        return  api().getLocalDateTime(refOrdinal);
    }

    public UserAPI api() {
        return typeApi().getAPI();
    }

    public UserTypeAPI typeApi() {
        return delegate().getTypeAPI();
    }

    protected UserDelegate delegate() {
        return (UserDelegate)delegate;
    }

    public String toString() {
        return new HollowRecordStringifier().stringify(this);
    }

}