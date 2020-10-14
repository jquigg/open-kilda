/* Copyright 2018 Telstra Open Source
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.openkilda.log.util;

import org.openkilda.log.constants.ActivityType;
import org.openkilda.log.dao.entity.UserActivityEntity;
import org.openkilda.log.model.ActivityTypeInfo;
import org.openkilda.log.model.LogInfo;

import org.usermanagement.dao.entity.UserEntity;
import org.usermanagement.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public final class LogConversionUtil {

    private LogConversionUtil() {

    }

    /**
     * Gets the user activity.
     *
     * @param info the info
     * @return the user activity
     */
    public static UserActivityEntity getUserActivity(final LogInfo info) {
        UserActivityEntity userActivity = new UserActivityEntity();
        userActivity.setUserId(info.getUserId());
        userActivity.setActivity(info.getActivityType().getActivityTypeEntity());
        userActivity.setObjectId(info.getObjectId());
        userActivity.setActivityTime(info.getActivityTime());
        userActivity.setClientIp(info.getClientIpAddress());
        return userActivity;
    }
    
    /**
     * Gets the log info.
     *
     * @param userActivity the user activity
     * @return the log info
     */
    public static LogInfo getLogInfo(final UserActivityEntity userActivity) {
        LogInfo info = new LogInfo();
        info.setUserId(userActivity.getUserId());
        info.setActivityType(ActivityType.getActivityById(userActivity.getActivity().getId()));
        info.setObjectId(userActivity.getObjectId());
        info.setActivityTime(userActivity.getActivityTime());
        info.setClientIpAddress(userActivity.getClientIp());
        return info;
    }

    /**
     * Gets the user list.
     *
     * @param userEntities the user entities
     * @return the user list
     */
    public static List<UserInfo> getUserInfo(List<UserEntity> userEntities) {
        List<UserInfo> userList = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            if (userEntity.getUserId() != 1) {
                userList.add(toUserInfo(userEntity));
            }
        }
        return userList;
    }

    private static UserInfo toUserInfo(UserEntity userEntity) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(userEntity.getEmail().toLowerCase());
        userInfo.setUsername(userEntity.getUsername().toLowerCase());
        userInfo.setStatus(userEntity.getStatusEntity().getStatus());
        userInfo.setUserId(userEntity.getUserId());
        return userInfo;
    }

    /**
     * Gets the activity types.
     *
     * @return the activity types
     */
    public static List<ActivityTypeInfo> getActivityTypeInfo() {
        List<ActivityTypeInfo> activityTypeInfos = new ArrayList<>();
        for (ActivityType activityType : ActivityType.values()) {
            activityTypeInfos.add(
                    new ActivityTypeInfo(activityType.getId(), activityType.getActivityTypeEntity().getActivityName()));
        }
        return activityTypeInfos;
    }
}
