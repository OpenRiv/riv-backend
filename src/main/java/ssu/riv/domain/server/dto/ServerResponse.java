package ssu.riv.domain.server.dto;

import lombok.Builder;
import lombok.Getter;

public abstract class ServerResponse {

    @Getter
    @Builder
    public static class AddServerInfo {
        private Long serverId;
    }

    // 특정 채널 id 반환
    @Getter
    @Builder
    public static class ChannelIdInfo {
        private Long channelId;
    }

    // 특정 서버 id 반환
    @Getter
    @Builder
    public static class ServerIdInfo {
        private Long serverId;
    }

}
