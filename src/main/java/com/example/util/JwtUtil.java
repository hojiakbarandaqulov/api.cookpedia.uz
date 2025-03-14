package com.example.util;

import com.example.enums.RoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 96; // 2-day
    private static final String secretKey = "verylongmazgiskjdhskjdhadasdasgfgdfgdfdftrhdgrgefergetdgsfegvergdgsbdzsfbvgdsetbgrFLKWRMFKJERNGVSFUOISNIUVNSDBFIUSHIULFHWAUOIESIUOFIOEJOIGJMKLDFMGghjgjOTFIJBP";

    public static String encode(String profileId, String email) {
        return Jwts
                .builder()
                .subject(email)
                .subject(String.valueOf(profileId))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (tokenLiveTime)))
                .signWith(getSignInKey())
                .compact();
    }
    public static String encode(String username, String profileId, RoleEnum roleList) {

        Map<String, String> claims = new HashMap<>();
        claims.put("roles", String.valueOf(roleList));
        claims.put("id", String.valueOf(profileId));

        return Jwts
                .builder()
                .subject(username)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (tokenLiveTime)))
                .signWith(getSignInKey())
                .compact();
    }

   /* public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        Integer id = Integer.valueOf((String) claims.get("id"));
        String strRoles = (String) claims.get("roles");
        List<ProfileRole> roleLis = Arrays.stream(strRoles.split(","))
                .map(ProfileRole::valueOf)
                .toList();
        return new JwtDTO(id, username, roleLis);
    }*/

    public static Integer decodeVerRegToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Integer.valueOf(claims.getSubject());
    }
    private static SecretKey getSignInKey() {
        byte[] keyBytes = Base64.getUrlDecoder().decode(secretKey);
        return  Keys.hmacShaKeyFor(keyBytes);
    }
}


