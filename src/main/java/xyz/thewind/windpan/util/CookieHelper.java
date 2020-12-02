package xyz.thewind.windpan.util;

import xyz.thewind.windpan.bean.Token;

public class CookieHelper {
    public static Token parseToken(String cookie){
        String userId="";
        String password="";
        Token token=new Token(userId,password);
        if(cookie==null){
            return token;
        }
        cookie=cookie.replaceAll(" ","");
        String[] cookies=cookie.split(";");
        if(cookies.length==0){
            return token;
        }
        for (String cookieSplice:cookies){
            String[] pair=cookieSplice.split("=");
            if(cookies.length!=2){
                return token;
            }
            if(pair[0].equals("userId")){
                token.setUserId(pair[1]);
            }
            if(pair[0].equals("password")){
                token.setPassword(pair[1]);
            }
        }
        return token;
    }
}
