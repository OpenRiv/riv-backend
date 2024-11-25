package ssu.riv.domain.server.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.riv.domain.server.entity.Server;
import ssu.riv.domain.server.repository.ServerRepository;
import ssu.riv.global.error.BusinessException;
import ssu.riv.global.error.code.RivErrorCode;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository; // Repository 주입

    @Override
    public Server addServer(String serverUnique) {

        // 1. serverUnique 중복 확인
        existServer(serverUnique);

        // 2. 서버 엔티티 생성 및 저장
        Server server = Server
                .builder()
                .serverUnique(serverUnique) // 고유 ID 설정
                .build();

        serverRepository.save(server); // DB에 저장

        return server; // 저장된 서버 엔티티 반환
    }

    // 이미 해당 서버가 존재하는지 확인하는 함수
    private void existServer(String serverUnique) {
        if (serverRepository.existsByServerUnique(serverUnique)) {
            throw new BusinessException(RivErrorCode.EXIST_SERVER);
        }
    }
}
