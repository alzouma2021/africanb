package com.africanb.africanb.helper.searchFunctions;

/*import ci.sgabs.gs.souscriptionApp.dao.entity.Users;
import ci.sgabs.gs.souscriptionApp.helper.enums.OperatorEnum;
import com.africanb.africanb.helper.enums.OperatorEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;*/


import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

//@Slf4j
public class Utilities {

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.isEmpty()) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    public static boolean isTrue(Boolean b) {
        return b != null && b;
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return (list != null && !list.isEmpty());
    }

    public static <T> boolean isEmpty(List<T> list) {
        return (list == null || list.isEmpty());
    }

   /*public static boolean notBlank(String str) {
        return str != null && !str.isEmpty() && !str.equals("\n")
                && org.apache.commons.lang3.StringUtils.isNotBlank(str);
    }*/

/*
    public static boolean blank(String str) {
        return !notBlank(str);
    }
*/

   /* public static <T> boolean searchParamIsNotEmpty(SearchParam<T> fieldParam) {
        return fieldParam != null && isNotBlank(fieldParam.getOperator())
                && OperatorEnum.IS_VALID_OPERATOR(fieldParam.getOperator())
                && ((OperatorEnum.IS_BETWEEN_OPERATOR(fieldParam.getOperator()) && fieldParam.getStart() != null
                && isNotBlank(fieldParam.getStart().toString()) && fieldParam.getEnd() != null
                && isNotBlank(fieldParam.getEnd().toString()))
                || (OperatorEnum.IS_IN_OPERATOR(fieldParam.getOperator()) && isNotEmpty(fieldParam.getDatas()))
                || (OperatorEnum.OPERATOR_NOT_NEEDS_ANY_VALUE(fieldParam.getOperator()))
                || (OperatorEnum.OPERATOR_NEEDS_FIELD_VALUE(fieldParam.getOperator())));
    }

    public static <T> boolean searchParamIsNotEmpty(SearchParam<T> fieldParam, T fieldValue) {
        return fieldParam != null && isNotBlank(fieldParam.getOperator())
                && OperatorEnum.IS_VALID_OPERATOR(fieldParam.getOperator())
                && ((OperatorEnum.IS_BETWEEN_OPERATOR(fieldParam.getOperator()) && fieldParam.getStart() != null
                && isNotBlank(fieldParam.getStart().toString()) && fieldParam.getEnd() != null
                && isNotBlank(fieldParam.getEnd().toString()))
                || (OperatorEnum.IS_IN_OPERATOR(fieldParam.getOperator()) && isNotEmpty(fieldParam.getDatas()))
                || (OperatorEnum.OPERATOR_NOT_NEEDS_ANY_VALUE(fieldParam.getOperator()))
                || (OperatorEnum.OPERATOR_NEEDS_FIELD_VALUE(fieldParam.getOperator()) && fieldValue != null
                && isNotBlank(fieldValue.toString())));
    }

    public static String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuffer.toString();
    }

    public static String encrypt(String str) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashedBytes = digest.digest(str.getBytes("UTF-8"));

        return convertByteArrayToHexString(hashedBytes);
    }

    public static String findDateFormatByParsing(String date) {
        if (blank(date)) {
            return null;
        }
        List<String> datasPatterns = new ArrayList<String>();
        datasPatterns.addAll(Arrays.asList("dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy", "ddMMyyyy"));
        datasPatterns.addAll(Arrays.asList("dd/MM/yyyy HH", "dd-MM-yyyy HH", "dd.MM.yyyy HH", "ddMMyyyy HH"));
        datasPatterns
                .addAll(Arrays.asList("dd/MM/yyyy HH:mm", "dd-MM-yyyy HH:mm", "dd.MM.yyyy HH:mm", "ddMMyyyy HH:mm"));
        datasPatterns.addAll(Arrays.asList("dd/MM/yyyy HH:mm:ss", "dd-MM-yyyy HH:mm:ss", "dd.MM.yyyy HH:mm:ss",
                "ddMMyyyy HH:mm:ss"));
        datasPatterns.addAll(Arrays.asList("dd/MM/yyyy HH:mm:ss.SSS", "dd-MM-yyyy HH:mm:ss.SSS",
                "dd.MM.yyyy HH:mm:ss.SSS", "ddMMyyyy HH:mm:ss.SSS"));

        datasPatterns.addAll(Arrays.asList("yyyy/MM/dd", "yyyy-MM-dd", "yyyy.MM.dd", "yyyyMMdd"));
        datasPatterns.addAll(Arrays.asList("yyyy/MM/dd HH", "yyyy-MM-dd HH", "yyyy.MM.dd HH", "yyyyMMdd HH"));
        datasPatterns
                .addAll(Arrays.asList("yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm", "yyyy.MM.dd HH:mm", "yyyyMMdd HH:mm"));
        datasPatterns.addAll(Arrays.asList("yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss",
                "yyyyMMdd HH:mm:ss"));
        datasPatterns.addAll(Arrays.asList("yyyy/MM/dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss.SSS",
                "yyyy.MM.dd HH:mm:ss.SSS", "yyyyMMdd HH:mm:ss.SSS"));

        datasPatterns.addAll(Arrays.asList("dd/MM", "dd-MM", "dd.MM", "ddMM"));
        datasPatterns.addAll(Arrays.asList("MM/yyyy", "MM-yyyy", "MM.yyyy", "MMyyyy"));

        datasPatterns.addAll(Arrays.asList("MM/dd", "MM-dd", "MM.dd", "MMdd"));
        datasPatterns.addAll(Arrays.asList("yyyy/MM", "yyyy-MM", "yyyy.MM", "yyyyMM"));

        datasPatterns.addAll(Arrays.asList("yyyy"));
        datasPatterns.addAll(Arrays.asList("HH", "HH:mm", "HH:mm:ss", "HH:mm:ss.SSS"));

        for (String pattern : datasPatterns) {
            try {
                // SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                // sdf.setLenient(false);
                // sdf.parse(date);

                org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
                formatter.parseDateTime(date);
                return pattern;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        return null;
    }
*/
    public static Date getCurrentDate() {
        return new Date();
    }

    public static boolean isValidID(Long id) {
        return id != null && id > 0;
    }
/*
    public static Integer getLoginUser() {//HttpRequest request doit etre en parametre
        return 1;
    }


    public static String createToken(Users users) {
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("id", users.getId())
                .claim("login", users.getLogin())
                .setSubject(users.getNom())
                .setId(users.getId().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
                .signWith(hmacKey)
                .compact();
        return jwtToken;
    }

    public static Jws<Claims> decodeToken(String jwtString) {
        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }

*/




}