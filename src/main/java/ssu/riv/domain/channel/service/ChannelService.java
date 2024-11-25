package ssu.riv.domain.channel.service;

import java.util.List;

public interface ChannelService {
    List<Long> addChannels(Long serverId, List<String> channelUniqueIds);
    List<Long> findChannelIds(Long serverId);
}
