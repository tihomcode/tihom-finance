package com.tihom.util;

import org.junit.Test;

/**
 * 测试加签验签
 * @author TiHom
 * create at 2018/8/4 0004.
 */
public class RSAUtilTest {
    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsYmTLuYEvuTduISqEdqlXSRvj\n" +
            "GCHK1Puicr5W75xI025i5AsJ3D4LQU5T36yDCQJ/A/wAU2GN5wkYXwADfU/goKxs\n" +
            "EiSx1dW+ufxZbl+b2QJph9Fc/rMS4cI7znOOcsMEi4p1/IJCRQAL7gCOC1DWKXzj\n" +
            "VNd830n9rTw5yt9sTQIDAQAB";
    private static final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKxiZMu5gS+5N24h\n" +
            "KoR2qVdJG+MYIcrU+6JyvlbvnEjTbmLkCwncPgtBTlPfrIMJAn8D/ABTYY3nCRhf\n" +
            "AAN9T+CgrGwSJLHV1b65/FluX5vZAmmH0Vz+sxLhwjvOc45ywwSLinX8gkJFAAvu\n" +
            "AI4LUNYpfONU13zfSf2tPDnK32xNAgMBAAECgYEAg00WtU4AplcPb2T3u5medouL\n" +
            "IDh7VMFRQXTgods0UQwqYkyMU+Bbqbr+bEhMYCp2qkRgp+bawXkepl+F5SKHubLj\n" +
            "cFP0YhDZwbZpQCAo9cgHAdToZvsUURDdMpcryOqqRdzhFFM1JWxnu4adG08yAYrH\n" +
            "fMH/Ykz8jpjGh+UDCYECQQDSK4YmHK/vOHTdFUttGiulIITnG9ICFjhm3kmpS3f+\n" +
            "QXm4DEVLsUElcwEopVqW9iMpI6HPs2zQVAh6t2Ij8LWxAkEA0fmIWiUV3VKYcIYn\n" +
            "4C1bBwu4mx40P1uBqQD0eLeMpwPH28vOwdCPYz1zEh10kDHfkC9elI5fhsGMLmEv\n" +
            "ra/bXQJBAKYkggfe5bXWi4u6KcY1ygrfijCobXv41N19G/4ZCuKUIAS+rokHtthD\n" +
            "8caP8O0l5uW+pUvsKzkFNS7NiWegAgECQHRTDHH8R5+kSWVVPTQZjGtb1/q/gexz\n" +
            "smJBcC6MaTSBiLBAuZtwAm/VNDGd9dyIdLU5OOmN8mgCQ7nSB4rueNUCQDQ51Z6V\n" +
            "KnI4pNGlkA71UnGMwbsxeFBTEULza7ZjnxmkVjH0FhtnFv3MEsSURREmoarkcYDk\n" +
            "LSuVOosBumC2uA0=";

    @Test
    public void signTest(){
        String text = "{\"amount\":10,\"chanId\":\"111\",\"chanUserId\":\"123\",\"createAt\":\"2018-12-365 14:38:40\",\"memo\":\"string\",\"outerOrderId\":\"10001\",\"productId\":\"T001\"}";
        String sign  = RSAUtil.sign(text,privateKey);
        System.out.println(sign);
        System.out.println(RSAUtil.verify(text, sign, publicKey));
    }
}
