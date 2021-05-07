
import org.rmj.gcardappfx.commands.OfflinePointsInquiry;



public class testOfflinePointsInquiry {
    public static void main(String [] args){       
        String argument [] = new String[5];

        argument[0] = "LRTrackr";
        argument[1] = "M001111122";
        argument[2] = "M00119000150";
        argument[3] = "555556";
        argument[4] = "2020-07-23";
        
        OfflinePointsInquiry.main(argument);
    }
}
