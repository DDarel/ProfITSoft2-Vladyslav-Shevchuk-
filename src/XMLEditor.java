import java.io.*;
import java.util.Scanner;
import java.util.regex.*;

public class XMLEditor {
    public static void main(String[] args) throws Exception{

        FileWriter fw = new FileWriter( "resources/NameSurname/output.xml" );
        fw.write("<persons>\n");

        FileReader fr = new FileReader("resources/NameSurname/input.xml");
        Scanner scan = new Scanner(fr);
        scan.nextLine();
        String tag = "";
        while (scan.hasNextLine()) {
            tag+= scan.nextLine() + "\n";
            if (tag.indexOf('/') != -1){
                String regexSurname = "\\s* surname\\s*=\\s*\"([^a]*)\"";
                Pattern pattern = Pattern.compile(regexSurname);
                Matcher matcherSurname = pattern.matcher(tag);
                if (matcherSurname.find()) {
                    String surname = matcherSurname.group(1);
                    tag = tag.replace(matcherSurname.group(), "");
                    String regexName = " name\\s*=\\s*\"([^a]*)\"";
                    Pattern patternName = Pattern.compile(regexName);
                    Matcher matcherName = patternName.matcher(tag);
                    if (matcherName.find()) {
                        String name = matcherName.group(1);
                        tag = tag.replace(name, name + " " + surname);
                        fw.write(tag);
                        tag = "";
                    }
                }
            }
        }
        fw.write("</persons>\n");
        fr.close();
        fw.close();
    }
}
