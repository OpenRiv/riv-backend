package ssu.riv.domain.channel.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public abstract class ChannelResponse {

    // 채널 추가시 정보 반환
    @Getter
    @Builder
    public static class AddChannelInfo {
        private Long serverId;
        private List<Long> channelIds; // 채널 ID들의 리스트
    }

    // 특정 채널 id 반환
    @Getter
    @Builder
    public static class ChannelIdInfo {
        private Long channelId;
    }

    // 특정 서버의 채널 리스트 반환
    @Getter
    @Builder
    public static class ChannelListInfo {
        private Long serverId;      // 서버 ID
        private List<Long> channelList; // 서버에 속한 채널 ID 리스트
    }
}
