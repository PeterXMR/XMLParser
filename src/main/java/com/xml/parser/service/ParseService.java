package com.xml.parser.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.xml.parser.Constants;
import com.xml.parser.MunicipalityDTO;
import com.xml.parser.entity.District;
import com.xml.parser.entity.Municipality;
import com.xml.parser.repository.DistrictRepository;
import com.xml.parser.repository.MunicipalityRepository;

import net.lingala.zip4j.ZipFile;

@Service
public class ParseService {

    @Value("${xml.file.url}")
    private String xmlFileUrl;
    private final MunicipalityRepository municipalityRepository;
    private final DistrictRepository districtRepository;

    public ParseService(MunicipalityRepository municipalityRepository, DistrictRepository districtRepository) {
        this.municipalityRepository = municipalityRepository;
        this.districtRepository = districtRepository;
    }

    public MunicipalityDTO loadAndSaveMunicipalityDTO() {

        MunicipalityDTO dto = new MunicipalityDTO();
        try {
            URL url = new URI(xmlFileUrl).toURL();
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(Constants.ZIP_FILE_NAME);
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            fileOutputStream.close();
            unzipFolderZip4j(Constants.SOURCE_ZIP_PATH, Constants.TARGET_FOLDER_PATH);
            dto = parse(Constants.XML_FILE_NAME);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        }

        saveMunicipality(dto);
        return dto;
    }

    private void saveMunicipality(MunicipalityDTO dto) {
        municipalityRepository.saveAll(dto.getMunicipalities());
        districtRepository.saveAll(dto.getDistricts());
    }

    @SuppressWarnings("resource")
    public static void unzipFolderZip4j(Path source, Path target)
            throws IOException {

        new ZipFile(source.toFile())
                .extractAll(target.toString());
    }

    public MunicipalityDTO parse(String path)
            throws StreamReadException, DatabindException, IOException, ParserConfigurationException, SAXException {
        File file = new File(Constants.XML_FILE_DIRECTORY);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        MunicipalityDTO dto = new MunicipalityDTO();
        List<Municipality> municipalityList = new ArrayList<>();
        List<District> districtList = new ArrayList<>();
        NodeList nodeList = doc.getElementsByTagName("vf:Obec");

        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node node = nodeList.item(i);
            Municipality municipality = new Municipality();

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element tElement = (Element) node;
                municipality.setCode(Integer.valueOf(tElement
                        .getElementsByTagName("obi:Kod")
                        .item(0)
                        .getTextContent()));
            }

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element tElement = (Element) node;
                municipality.setName(tElement
                        .getElementsByTagName("obi:Nazev")
                        .item(0)
                        .getTextContent());
            }

            municipalityList.add(municipality);
        }

        dto.setMunicipalities(municipalityList);
        NodeList districtNodeList = doc.getElementsByTagName("vf:CastObce");

        for (int i = 0; i < districtNodeList.getLength(); ++i) {
            Node node = districtNodeList.item(i);
            District district = new District();

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element tElement = (Element) node;
                district.setCode(Integer.valueOf(tElement
                        .getElementsByTagName("coi:Kod")
                        .item(0)
                        .getTextContent()));
            }

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element tElement = (Element) node;
                district.setName(tElement
                        .getElementsByTagName("coi:Nazev")
                        .item(0)
                        .getTextContent());
            }

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element tElement = (Element) node;
                district.setMunicipalityCode(Integer.valueOf(tElement
                        .getElementsByTagName("obi:Kod")
                        .item(0)
                        .getTextContent()));
            }

            districtList.add(district);
        }
        dto.setDistricts(districtList);
        return dto;
    }
}
