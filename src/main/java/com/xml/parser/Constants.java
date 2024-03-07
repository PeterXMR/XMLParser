package com.xml.parser;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    private static final String SOURCE_ZIP = "/Users/accountname/Desktop/parser/kopidlno.xml.zip";
    private static final String TARGET_FOLDER = "/Users/accountname/Desktop/parser/unzipped";
    public static final Path SOURCE_ZIP_PATH = Paths.get(SOURCE_ZIP);
    public static final Path TARGET_FOLDER_PATH = Paths.get(TARGET_FOLDER);
    public static final String XML_FILE_NAME = "20210331_OB_573060_UZSZ.xml";
    public static final String ZIP_FILE_NAME = "kopidlno.xml.zip";
    public static final String XML_FILE_PATH = "/Users/accountname/Desktop/parser/unzipped/20210331_OB_573060_UZSZ.xml";

}
