
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fee {
    private String dateTime;
    private String firstName;
    private String lastName;
    private String type;
    private float fineAmount;

    public Fee(){}
    public Fee(String dateTime, String firstName, String lastName, String type, float fineAmount) {
        this.dateTime = dateTime;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.fineAmount = fineAmount;
    }

    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }
    public void setDateTime(LocalDateTime dateTime){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.dateTime = dateTime.format(myFormatObj); ;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFineAmount(float fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getDateTime(){
        return dateTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getType() {
        return type;
    }

    public float getFineAmount() {
        return fineAmount;
    }
}
