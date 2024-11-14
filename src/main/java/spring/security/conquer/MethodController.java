package spring.security.conquer;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
class MethodController {

    private final DataService dataService;

    public MethodController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/writeList")
    public List<Account> writeList(@RequestBody List<Account> data) {
        return dataService.wirteList(data);
    }

    @PostMapping("/writeMap")
    public Map<String, Account> writeMap(@RequestBody List<Account> data) {
        return dataService.wirteMap(data.stream().collect(Collectors.toMap(Account::owner, account -> account)));
    }

    @GetMapping("/readList")
    List<Account> readList() {
        return dataService.readList();
    }

    @GetMapping("/readMap")
    Map<String, Account> readMap() {
        return dataService.readMap();
    }

}

@Service
class DataService {

    @PreFilter("filterObject.owner == authentication.name")
    List<Account> wirteList(List<Account> data) {
        return data;
    }

    @PreFilter("filterObject.key == authentication.name")
    public Map<String, Account> wirteMap(Map<String, Account> data) {
        return data;
    }

    @PostFilter("filterObject.owner == authentication.name")
    public List<Account> readList() {
        return new ArrayList<>(List.of(
                new Account("user", false),
                new Account("admin", false),
                new Account("db", false)
        ));
    }

    @PostFilter("filterObject.value.owner() == authentication.name")
    public Map<String, Account> readMap() {
        return new HashMap<>(Map.of(
                "user", new Account("user", false),
                "admin", new Account("admin", false),
                "db", new Account("db", false)
        ));
    }

}

record Account(
        String owner,
        boolean isSecure
) {
}
