package ssu.riv.domain.channel.service;

import ssu.riv.domain.channel.entity.Channel;

import java.util.List;

public interface ChannelService {
    List<Long> addChannels(Long serverId, List<String> channelUniqueIds);
    Channel getChannelId(String channelUnique);
    List<Long> getChannelList(Long serverId);
    List<Long> getGuildChannel(Long guildId);
    List<String> getCategoriesByChannel(Long channelId);
}
