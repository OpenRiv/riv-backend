package ssu.riv.domain.server.service;

import ssu.riv.domain.server.entity.Server;

public interface ServerService {
    Server addServer(String serverUnique);
    Server getServerId(String serverUnique);
}
