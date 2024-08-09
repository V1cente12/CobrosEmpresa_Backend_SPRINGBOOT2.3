package cobranza.v2.pgt.com.config.JWT;

public class JwtConfigKey {
    /**
     * comamd generate OPENSSL KEY public and private
     * 
     * cmd:------------------------------------------------------------------------------
     * 
     * openssl genrsa -out jwt.pem -------------Genera la salida de las claves
     * 
     * openssl rsa -in jwt.pem -----------------Genera la entrada de la clave privada
     * 
     * openssl rsa -in jwt.pem -----------------Genera la entrada de la clave publica
     * 
     */

    public static final String RSA_Publica = "-----BEGIN PUBLIC KEY-----\r\n"
        + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuTe38L9isCleW3/jC9uj\r\n"
        + "A9FnguSQ0MnAZf/qercoFH41WF/CJoYgb6fb7tJAfbIUSJssrItDXX4gywd0bi7R\r\n"
        + "3YnQccsHvrqpA5qbVo2G8rwcoMt5+hCaDWddHebpTvLYTYeKkxeSQ8AUKQMGXSsb\r\n"
        + "Yr+AR8l8WHgz+BY9Asj00fuaWxr+xngrCggQfn7F216S03XbEKNuoc6bTDimKas6\r\n"
        + "wN9qHw5a6D/x9dpW4fetNOlcM6sJFyRR+4DOxVmCrBQ8ML0xZxl37eLAS4bGT39a\r\n"
        + "uXqoUVbrcL/DQWUyqJeqICs+wC4eytvo3xAxSEwjlwhhNBu4QzVm4/LUnQTPPqdg\r\n" + "twIDAQAB\r\n" + "-----END PUBLIC KEY-----";

    public static final String RSA_Private = "--------BEGIN RSA PRIVATE KEY-----\r\n"
        + "MIIEpQIBAAKCAQEAuTe38L9isCleW3/jC9ujA9FnguSQ0MnAZf/qercoFH41WF/C\r\n"
        + "JoYgb6fb7tJAfbIUSJssrItDXX4gywd0bi7R3YnQccsHvrqpA5qbVo2G8rwcoMt5\r\n"
        + "+hCaDWddHebpTvLYTYeKkxeSQ8AUKQMGXSsbYr+AR8l8WHgz+BY9Asj00fuaWxr+\r\n"
        + "xngrCggQfn7F216S03XbEKNuoc6bTDimKas6wN9qHw5a6D/x9dpW4fetNOlcM6sJ\r\n"
        + "FyRR+4DOxVmCrBQ8ML0xZxl37eLAS4bGT39auXqoUVbrcL/DQWUyqJeqICs+wC4e\r\n"
        + "ytvo3xAxSEwjlwhhNBu4QzVm4/LUnQTPPqdgtwIDAQABAoIBAAl8xipJ02dMpy2u\r\n"
        + "rlWkKYR/jHrS9GwNeZJ/+mXQzffGwzrEpPBOPIkboxNnp7AImCtj73TjKU83r7xW\r\n"
        + "pGskcNGVRp0dlkb5zsCFgeF8HGaApkLCSXw6r3lySv3GHztRCy1lfP6t3WslAqNb\r\n"
        + "KU9Kbvuu7YY/1uO9PIQ/t15WJtw+nZ3H6gl2pXHjuhSlrivttK5hKxMduAFxK639\r\n"
        + "oZgvrfIOOVtWfQgdxMn0rBX45JHcbvKS/N48ojvsRVsjvJeA+QSVwXTVoWVV2s2I\r\n"
        + "yJ0GTFO2RgNnvgxX3UCimLwvc7bXSOnUyWY2bEDjhIAVHKTArW+VO/YnyrvAzJso\r\n"
        + "1XU/HBkCgYEA9nTmwjlUTnR1P9cssJT1Be13fmVBROYqMbQ8zvBYVXjW0MZhyF0e\r\n"
        + "XHRi8Io9J2J0ajcNTqMwQHj2kWGR7WJIH0z6UJMkssO/QecO1+BZXMhgkZLXa6FK\r\n"
        + "v6wgnfh0Ybl5gbb5DhIaDquyOmzE+lpB5WP7VSNGD4xK7KsVnqLYW8MCgYEAwGPC\r\n"
        + "7iD0DfV+YUmd+WTs0jYAFM/tU1ueiAIwL4uV/jz2kg8YrcxFbsmSZGMXvuHcXdDZ\r\n"
        + "QKagh4QG0zTfoAW7S9KLSFeSYQoqzdL/qQ2yW28cjNEfrxWLngt67MiGu0zrQZCD\r\n"
        + "Sf3v9AdOm00uzF1jtuS90dKNIO2xLqPP4HRqe/0CgYEAgEMIkQ369TWgi4/1PxTW\r\n"
        + "i7YxGQOjOzM5xFJTzzLAFgNkcJS98OOdWGptz1dyShvwEBMEhudgxjztvW1034Dl\r\n"
        + "nfyWzPY3BB3hXG/ehrTIbQQPVdOA7w+q8iOnee8CypUXSk2qQgtjqjzq7YML5Gqy\r\n"
        + "nqow8/b+kDroB1FmV//R77cCgYEApST9butak6DnmxtWP34iy08PQs45KhS7QISg\r\n"
        + "WC+50UMDZN0Qkh+GelMNFp75AyFdyCo+UwC1S55JPYNlcjd5TtysMqz5OdNAau10\r\n"
        + "B+l58IKwPzBDwkb8Ue4I1rhK7vxX9S/Xguw3zYF4OFdIlKSVs6kmR7Y07s8aCK8V\r\n"
        + "AdbskQECgYEA8gh6rNsvQGaJhzD2gP/NnxNxhZgxgWrmxiHACzEdgoNpxl4If1eM\r\n"
        + "sqm8ntz7bpwcae/d7MVXTfWyGncXNYPitsCbwBaN83RehmzL7//+vlL2B7pOC27m\r\n"
        + "tqooX5GGjaXwF4b8Rao1JsGFtlrALLBKI50TI5BUlp1rYMMGtVXBLRs=\r\n" + "-----END RSA PRIVATE KEY-----";
}
