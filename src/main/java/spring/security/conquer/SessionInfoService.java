package spring.security.conquer;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SessionInfoService {

    private final SessionRegistry sessionRegistry;

    void sessionInfo() {
        sessionRegistry.getAllPrincipals()
                .stream()
                .flatMap(p -> sessionRegistry.getAllSessions(p, false).stream())
                .forEach(sessionInformation -> System.out.println("사용자: " + sessionInformation.getPrincipal() + " 세션ID: " + sessionInformation.getSessionId() + " 최종 요청 시간: " + sessionInformation.getLastRequest()));
    }
}
