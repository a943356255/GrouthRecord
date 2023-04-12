package com.example.springboot_vue.leetcode_design;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationManager {

    public int timeToLive;

    public Map<String, Integer> map = new HashMap<>();

    public AuthenticationManager(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void generate(String tokenId, int currentTime) {
        map.put(tokenId, currentTime);
    }

    public void renew(String tokenId, int currentTime) {
        if (map.get(tokenId) == null) {
            return;
        }

        int time = map.get(tokenId);
        if (time + timeToLive > currentTime) {
            map.put(tokenId, currentTime);
        }
    }

    public int countUnexpiredTokens(int currentTime) {
        int res = 0;

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() + timeToLive > currentTime) {
                res++;
            }
        }

        return res;
    }

}
