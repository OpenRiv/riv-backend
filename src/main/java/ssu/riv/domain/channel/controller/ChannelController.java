package ssu.riv.domain.channel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssu.riv.domain.channel.converter.ChannelConverter;
import ssu.riv.domain.channel.dto.ChannelRequest;
import ssu.riv.domain.channel.dto.ChannelResponse;
import ssu.riv.domain.channel.entity.Channel;
import ssu.riv.domain.channel.service.ChannelService;
import ssu.riv.global.result.ResultResponse;
import ssu.riv.global.result.code.RivResultCode;

import java.util.List;

@RestController
@RequestMapping("/servers")
@Tag(name = "02. 채널 API", description = "채널 도메인의 API입니다.")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;
    private final ChannelConverter channelConverter;

    // 여러 채널 id를 저장하는 API
    @PostMapping("/{serverId}/channels")
    @Operation(summary = "채널 id 저장 API", description = "여러 채널의 unique id를 저장하는 api입니다.")
    public ResultResponse<ChannelResponse.AddChannelInfo> addChannel(@PathVariable("serverId") Long serverId,
                                                                     @RequestBody ChannelRequest.UniqueChannelIds request) {
        // unique ID 기반으로 채널 저장 후, DB에서 생성된 Long ID 반환
        List<Long> savedChannelIds = channelService.addChannels(serverId, request.getChannelList());

        // Response 변환
        return ResultResponse.of(RivResultCode.ADD_Channel,
                channelConverter.toAddChannel(serverId, savedChannelIds));
    }

    // 채널 Unique id로 채널 id 조회 API
    @GetMapping("/channel")
    @Operation(summary = "채널 id 조회 API", description = "채널의 unique id로 채널 id를 조회하는 API입니다.")
    public ResultResponse<ChannelResponse.ChannelIdInfo> getChannelId(@RequestParam("channelUnique") String channelUnique) {
        Channel channel = channelService.getChannelId(channelUnique);

        return ResultResponse.of(RivResultCode.GET_CHANNEL_ID,
                channelConverter.toChannelId(channel));
    }
}