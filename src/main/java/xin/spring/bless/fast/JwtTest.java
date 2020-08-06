package xin.spring.bless.fast;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTest {

    public static void main(String[] args) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + 36 * 1000);
        String compact = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("1")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
        System.out.println(compact);
        Claims secret = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(compact)
                .getBody();
        System.out.println(secret.toString());
        System.out.println(secret.getSubject());
    }

}
