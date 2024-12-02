package ssu.riv.domain.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssu.riv.domain.channel.entity.Channel;
import ssu.riv.domain.server.converter.ServerConverter;
import ssu.riv.domain.server.dto.ServerResponse;
import ssu.riv.domain.server.entity.Server;
import ssu.riv.domain.server.service.ServerService;
import ssu.riv.global.result.ResultResponse;
import ssu.riv.global.result.code.RivResultCode;

@RestController
@RequestMapping("/servers")
@Tag(name = "01. 서버 API", description = "서버 도메인의 API입니다.")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;
    private final ServerConverter serverConverter;

    // 서버의 unique id 저장 api
    @PostMapping("")
    @Operation(summary = "서버 id 저장 API", description = "서버의 unique id를 저장하는 api입니다.")
    public ResultResponse<ServerResponse.AddServerInfo> addServer(@RequestParam("serverUnique")
                                                                     String serverUnique) {
        Server addServer = serverService.addServer(serverUnique);
        return ResultResponse.of(RivResultCode.ADD_Server,
                serverConverter.toAddServer(addServer));
    }

    // 서버 unique id로 서버 id 조회 API
    @GetMapping("/server")
    @Operation(summary = "서버 id 조회 API", description = "서버의 unique id로 서버 id를 조회하는 API입니다.")
    public ResultResponse<ServerResponse.ServerIdInfo> getServerId(@RequestParam("serverUnique") String serverUnique) {
        Server server = serverService.getServerId(serverUnique);

        return ResultResponse.of(RivResultCode.GET_SERVER_ID,
                serverConverter.toServerId(server));
    }

}