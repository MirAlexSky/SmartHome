import com.sun.media.jfxmedia.logging.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sun.plugin.dom.DOMObjectFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.logging.*;

import java.net.*;

public class NetConnection {

    protected static Boolean isLightOn;

    public static void main(String[] args) throws Exception {

        while(true) {
            Document smartHomeDOM = getUTF();
            if (smartHomeDOM == null) {
                System.out.println("Что то пошло не так, от сервера нет ответа");
            } else {
                Node lightNode = smartHomeDOM.getElementsByTagName("light").item(0);
                String light = lightNode.getTextContent();
                isLightOn = Boolean.valueOf(light);

                System.out.println(isLightOn);
            }

            Thread.sleep(2000);
        }
    }

    @Nullable
    protected static Document getUTF() throws Exception {

        URL url = new URL("http://smarthome.mir/xml_get.php");

        URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inLine;
        String line = "";

        while ((inLine = in.readLine()) != null)
            line = line.concat(inLine);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        InputSource is = new InputSource(new StringReader(line));

        return builder.parse(is);
    }
}
