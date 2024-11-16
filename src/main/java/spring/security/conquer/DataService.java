package spring.security.conquer;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
class DataService {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    String getUser() {
        return "user";
    }

    @PostAuthorize("returnObject.owner == authentication.name")
    Account getOwner(String name) {
        return new Account(name, false);
    }

    String display() {
        return "display";
    }

}
