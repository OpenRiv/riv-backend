package ssu.riv.domain.channel.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ssu.riv.domain.channel.dto.ChannelResponse;
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
}
