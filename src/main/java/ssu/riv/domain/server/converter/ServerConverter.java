package ssu.riv.domain.server.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ssu.riv.domain.server.dto.ServerResponse;
import ssu.riv.domain.server.entity.Server;

@Component
@RequiredArgsConstructor
public class ServerConverter {

    // Server 엔티티를 AddServerInfo DTO로 변환
    public ServerResponse.AddServerInfo toAddServer(Server server) {
        return ServerResponse.AddServerInfo
                .builder()
                .serverId(server.getId()) // 엔티티의 ID 가져오기
                .build();
    }

    public ServerResponse.ServerIdInfo toServerId(Server server) {
        return ServerResponse.ServerIdInfo
                .builder()
                .serverId(server.getId()) // DB에서 생성된 Long 타입 채널 ID
                .build();
    }

}
