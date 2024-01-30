package com.ll.hype.global.s3.image;

public enum ImageType {
    MEMBER("MEMBER"),
    REVIEW("REVIEW"),
    SOCIAL("SOCIAL"),
    QUESTION("QUESTION"),
    BRAND("BRAND");

    private String value;

    ImageType(String value) {

    }
    public static String getTypeName(ImageType type) {
        for (ImageType imageType : ImageType.values()) {
            if (imageType == type) {
                return type.value;
            }
        }
        throw new IllegalArgumentException("맞는 타입이 없습니다");
    }
}
