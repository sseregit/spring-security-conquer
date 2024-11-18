package spring.security.conquer;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
class AsyncService {

    @Async
    void asyncMethod() {
        SecurityContext securityContext1 = SecurityContextHolder.getContextHolderStrategy().getContext();
        println(securityContext1);
    }

    private void println(SecurityContext securityContext) {
        System.out.println("Thread = " + Thread.currentThread().getName() + "\nsecurityContext = " + securityContext);
    }

}
