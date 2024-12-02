package ssu.riv.domain.channel.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ssu.riv.domain.channel.dto.ChannelResponse;
import ssu.riv.domain.channel.entity.Channel;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChannelConverter {
    public ChannelResponse.AddChannelInfo toAddChannel(Long serverId, List<Long> channelIds) {
        return ChannelResponse.AddChannelInfo
                .builder()
                .serverId(serverId)
                .channelIds(channelIds)
                .build();
    }

    public ChannelResponse.ChannelIdInfo toChannelId(Channel channel) {
        return ChannelResponse.ChannelIdInfo
                .builder()
                .channelId(channel.getId()) // DB에서 생성된 Long 타입 채널 ID
                .build();
    }

    public ChannelResponse.ChannelListInfo toChannelListInfo(Long serverId, List<Long> channelIds) {
        return ChannelResponse.ChannelListInfo
                .builder()
                .serverId(serverId)
                .channelList(channelIds)
                .build();
    }
}
