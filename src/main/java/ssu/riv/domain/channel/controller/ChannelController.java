package ssu.riv.domain.channel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import ssu.riv.domain.recoding.converter.RecodingConverter;
import ssu.riv.domain.recoding.dto.RecodingResponse;
import ssu.riv.domain.recoding.entity.Recoding;
import ssu.riv.domain.recoding.service.RecodingService;
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
    private final RecodingService recodingService;
    private final RecodingConverter recodingConverter;

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

    // 특정 서버의 채널 목록 조회 API
    @GetMapping("/{serverId}/channels")
    @Operation(summary = "서버의 채널 목록 조회 API", description = "특정 서버에 속한 모든 채널 ID를 조회하는 API입니다.")
    public ResultResponse<ChannelResponse.ChannelListInfo> getChannelList(@PathVariable Long serverId) {

        // 채널 목록 조회
        List<Long> channelIdList = channelService.getChannelList(serverId);

        // 컨버터를 통해 응답 변환
        return ResultResponse.of(RivResultCode.GET_CHANNEL_LIST,
                channelConverter.toChannelListInfo(serverId, channelIdList));
    }

    // 특정 채널의 요약본 텍스트 파일 목록 조회 API (페이징)
    @GetMapping("/channels/{channelId}")
    @Operation(summary = "요약본 목록 조회 API", description = "특정 채널의 요약본 텍스트 파일 목록을 페이징 처리하여 조회하는 API입니다.")
    @Parameters(value = {
            @Parameter(name = "categoryName", description = "조회할 카테고리명(아무것도 안 넣으면 전체 조회)"),
            @Parameter(name = "isdesc", description = "정렬 순서(true면 내림차순, false면 오름차순)"),
            @Parameter(name = "search", description = "검색할 단어(아무것도 안 넣으면 검색 필터 없음)"),
            @Parameter(name = "page", description = "조회할 페이지를 입력하세요 (0부터 시작)"),
            @Parameter(name = "size", description = "페이지당 표시할 요약본 개수를 입력하세요.")
    })
    public ResultResponse<RecodingResponse.PagedRecodingInfo> getRecodingList(
            @PathVariable Long channelId,
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "isdesc", defaultValue = "true") boolean isdesc,
            @RequestParam(value = "search", required = false) String search,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            @Parameter(hidden = true) Pageable pageable) {

        // isdesc 값에 따라 정렬 방향 결정
        Sort sort = Sort.by(isdesc ? Sort.Direction.DESC : Sort.Direction.ASC, "createdAt");

        // 기존 Pageable을 새로운 Sort로 재생성
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        // categoryName, search 값 유무에 따른 조회 로직
        Page<Recoding> recodingList;
        boolean hasCategory = categoryName != null && !categoryName.isEmpty();
        boolean hasSearch = search != null && !search.isEmpty();

        if (!hasCategory && !hasSearch) {
            // 카테고리 없음, 검색어 없음 → 전체 조회
            recodingList = recodingService.getRecodingList(channelId, sortedPageable);
        } else if (hasCategory && !hasSearch) {
            // 카테고리 있음, 검색어 없음
            recodingList = recodingService.getRecodingListByChannelAndCategory(channelId, categoryName, sortedPageable);
        } else if (!hasCategory && hasSearch) {
            // 카테고리 없음, 검색어 있음
            recodingList = recodingService.getRecodingListByChannelAndSearch(channelId, search, sortedPageable);
        } else {
            // 카테고리 있음, 검색어 있음
            recodingList = recodingService.getRecodingListByChannelCategoryAndSearch(channelId, categoryName, search, sortedPageable);
        }

        // 페이징 데이터 변환 및 응답
        return ResultResponse.of(RivResultCode.GET_RECODING_LIST,
                recodingConverter.toPagedRecodingInfo(recodingList));
    }

    @GetMapping("/channels/guilds/{guildId}")
    @Operation(summary = "채널 정보 조회 API", description = "guildId를 통해 디스코드 서버로부터, 채널 정보를 조회하는 API입니다.")
    public ResultResponse<ChannelResponse.ChannelListInfo> getDiscordChannel(@PathVariable Long guildId) {

        // 채널 목록 조회
        List<Long> channelIdList = channelService.getGuildChannel(guildId);

        // 컨버터를 통해 응답 변환
        return ResultResponse.of(RivResultCode.GET_CHANNEL_LIST,
                channelConverter.toChannelListInfo(guildId, channelIdList));
    }

    @GetMapping("/channels/{channelId}/categories")
    @Operation(summary = "채널에 엮인 카테고리명 리스트 조회 API", description = "특정 channelId에 속한 카테고리의 목록을 조회합니다.")
    public ResultResponse<ChannelResponse.CategoryListInfo> getCategoriesByChannel(@PathVariable Long channelId) {
        List<String> categories = channelService.getCategoriesByChannel(channelId);
        return ResultResponse.of(RivResultCode.CATEGORY_LIST,
                channelConverter.toCategoriListInfo(channelId, categories));
    }
}