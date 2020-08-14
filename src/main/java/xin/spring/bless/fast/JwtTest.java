package xin.spring.bless.fast;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import xin.spring.bless.fast.commons.lang.factory.ThreadFactory;
import xin.spring.bless.fast.conf.StorageGlobalConfig;
import xin.spring.bless.fast.data.cache.DataCacheMemory;
import xin.spring.bless.fast.service.Storage;
import xin.spring.bless.fast.service.impl.LocalStorageServiceImpl;

import java.io.File;
import java.util.Date;

public class JwtTest {

    public static void main(String[] args) {
//        Date nowDate = new Date();
//        //过期时间
//        Date expireDate = new Date(nowDate.getTime() + 36 * 1000);
//        String compact = Jwts.builder()
//                .setHeaderParam("typ", "JWT")
//                .setSubject("1")
//                .setIssuedAt(nowDate)
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS512, "secret")
//                .compact();
//        System.out.println(compact);
//        Claims secret = Jwts.parser()
//                .setSigningKey("secret")
//                .parseClaimsJws(compact)
//                .getBody();
//        System.out.println(secret.toString());
//        System.out.println(secret.getSubject());
        StorageGlobalConfig.get().build();
        ThreadFactory.getInstance().execute(()->{
            LocalStorageServiceImpl service = (LocalStorageServiceImpl)DataCacheMemory.get().getService(Storage.class);
            File file = new File("C:\\新建文件夹");
            File[] files = file.listFiles();
            for (File f: files) {
                System.out.println("文件上传中：" + f.getName());
                service.upload(f);
                System.out.println("文件上传完成：" + f.getName());
            }
        });
        ThreadFactory.getInstance().shutdown();
    }

}
