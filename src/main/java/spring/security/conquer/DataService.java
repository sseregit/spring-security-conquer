package spring.security.conquer;

import org.springframework.stereotype.Service;

@Service
class DataService {

    String getUser() {
        return "user";
    }

    Account getOwner(String name) {
        return new Account(name, false);
    }

    String display() {
        return "display";
    }

}
