package org.aslstd.api.ejcore.external;

public enum FileType{
    JAR, HASH, INFO;

    @Override
    public String toString(){
        return "."+name().toLowerCase();
    }
}
