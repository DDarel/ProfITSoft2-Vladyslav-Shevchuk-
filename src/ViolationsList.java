import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.json.JSONObject;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViolationsList {
    private static ArrayList<Fee> fees = new ArrayList<>();
    private static HashMap<Integer,Float> yearMaxMap = new HashMap<>();
    private static HashMap<Integer,Float> yearTotalMap = new HashMap<>();

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(new File("resources/Violations/input.xml"), handler);

        String path = "resources/Violations/output.json";
        JSONObject json = new JSONObject();
        FileWriter out = new FileWriter(path);

        String[] arr = new String[yearMaxMap.size()];
        int size = yearMaxMap.size();
        for (int i = 0; i < size; i++){
            float max = 0;
            int year = 0;
            for (Map.Entry<Integer, Float> pair: yearMaxMap.entrySet()) {
                if (pair.getValue() > max){
                    max = pair.getValue();
                    year = pair.getKey();
                }
            }
            arr[i] = "#" + (i+1) + ". " + year + " total is " + yearTotalMap.get(year) + " with highest fine: " + max;
            json.put("Number " + (i+1), arr[i]);
            yearMaxMap.remove(year);
        }
        out.write(json.toString());
        out.close();
    }

    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("violation")) {
                String dateTime = attributes.getValue("date_time");
                String firstName = attributes.getValue("first_name");
                String lastName = attributes.getValue("last_name");
                String type = attributes.getValue("type");
                float fineAmount = Float.parseFloat(attributes.getValue("fine_amount"));

                Integer year = Integer.valueOf(dateTime.substring(0,4));
                if (!yearMaxMap.containsKey(year)){
                    yearMaxMap.put(year, fineAmount);
                } else if (yearMaxMap.get(year) < fineAmount) {
                    yearMaxMap.put(year, fineAmount);
                }

                if (!yearTotalMap.containsKey(year)){
                    yearTotalMap.put(year, fineAmount);
                } else {
                    yearTotalMap.put(year, yearTotalMap.get(year) + fineAmount);
                }

                fees.add(new Fee(dateTime, firstName, lastName, type, fineAmount));
            }
        }
    }

}
