package ssu.riv.domain.channel.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public abstract class ChannelResponse {

    @Getter
    @Builder
    public static class AddChannelInfo {
        private Long serverId;
        private List<Long> channelIds; // 서버에 포함된 채널 ID들의 리스트
    }

}
