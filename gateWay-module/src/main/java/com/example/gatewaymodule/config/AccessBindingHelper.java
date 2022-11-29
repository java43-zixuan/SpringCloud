package com.example.gatewaymodule.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ld
 */
@Component
public class AccessBindingHelper {
    private List<CompiledEntry> blacklist;
    private List<CompiledEntry> whitelist;
    private List<String> admins;

    @Resource
    private AccessBindingProperties setting;

    @PostConstruct
    public void init() {
        if (setting == null) {
            blacklist = new ArrayList<>();
            whitelist = new ArrayList<>();
            admins = new ArrayList<>();
        } else {
            blacklist = Optional.ofNullable(setting.getBlacklist()).orElse(new ArrayList<>()).stream()
                    .filter(this::isValid).map(CompiledEntry::from).collect(Collectors.toList());
            whitelist = Optional.ofNullable(setting.getWhitelist()).orElse(new ArrayList<>()).stream()
                    .filter(this::isValid).map(CompiledEntry::from).collect(Collectors.toList());
            admins = Optional.ofNullable(setting.getAdmin()).orElse(new ArrayList<>());
        }
    }

    public AccessBindingProperties getSetting() {
        return setting;
    }

    public boolean canAccess(String url, String partnerCode, String accountName) {
        if (admins.contains(accountName)) {
            return true;
        }
        boolean isInBlack = isMatchedInList(url, partnerCode, accountName, blacklist);
        // 与黑名单配置规则向匹配的不能访问
        boolean canAccess = !isInBlack;
        boolean isUrlInWhite = isMatchedInList(url, whitelist);
        boolean isInWhite = isMatchedInList(url, partnerCode, accountName, whitelist);
        // 白名单中配置的url，租户与用户不在白名单则不能访问
        return canAccess && (!isUrlInWhite || isInWhite);
    }

    private boolean isValid(AccessBindingProperties.Entry entry) {
        return isNotBlank(entry.getUrlPattern()) && (isNotBlank(entry.getPartnerCode()) || isNotBlank(entry.getUser()));
    }

    private static boolean isNotBlank(String s) {
        return s != null && s.trim().length() != 0;
    }

    private static boolean isMatchedInList(String url, String partnerCode, String accountName, List<CompiledEntry> list) {
        for (CompiledEntry entry : list) {
            if (isMatched(url, entry.urlPattern) && isMatched(partnerCode, entry.partnerCodePattern)
                    && isMatched(accountName, entry.userPattern)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMatchedInList(String url, List<CompiledEntry> list) {
        for (CompiledEntry entry : list) {
            if (isMatched(url, entry.urlPattern)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMatched(String s, Pattern p) {
        return p == null || p.matcher(s).matches();
    }

    private static class CompiledEntry {
        private Pattern urlPattern;
        private Pattern partnerCodePattern;
        private Pattern userPattern;

        private static CompiledEntry from(AccessBindingProperties.Entry entry) {
            CompiledEntry r = new CompiledEntry();
            r.urlPattern = toPattern(entry.getUrlPattern());
            if (entry.getPartnerCode() != null) {
                r.partnerCodePattern = toOrPattern(entry.getPartnerCode());
            }
            if (entry.getUser() != null) {
                r.userPattern = toOrPattern(entry.getUser());
            }
            return r;
        }

        private static Pattern toPattern(String s) {
            return Pattern.compile(toRegex(s));
        }

        private static String toRegex(String s) {
            return "^" + s.replaceAll("\\*", ".*") + "$";
        }

        private static Pattern toOrPattern(String s) {
            String regex = Stream.of(s.split(",")).map(CompiledEntry::toRegex).collect(Collectors.joining("|"));
            return Pattern.compile(regex);
        }
    }
}
