package ssu.riv.domain.server.dto;

import lombok.Builder;
import lombok.Getter;

public abstract class ServerResponse {

    @Getter
    @Builder
    public static class AddServerInfo {
        private Long serverId;
    }

}
