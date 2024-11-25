package ssu.riv.domain.channel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class ChannelRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UniqueChannelIds {
        @NotNull(message = "채널이 없을 수는 없습니다.")
        private List<String> channelList; // 채널 ID 리스트
    }
}
