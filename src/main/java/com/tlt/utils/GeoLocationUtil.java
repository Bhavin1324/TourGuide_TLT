
package com.tlt.utils;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;
import static com.tlt.constants.PathConstants.GEO_LOCATION_DB;
import static com.tlt.utils.Utils.CrossFetch_GET;
import java.io.File;
import java.net.InetAddress;


public class GeoLocationUtil {
    public static Location getUserLocation(){
        try{
            File database = new File(GEO_LOCATION_DB);
            DatabaseReader reader = new DatabaseReader.Builder(database).build();
            
            // for string reponse user url = https://api.ipify.org
            // for json response use url = "https://api.ipify.org?format=json"
            
            // Other apis for same purpose
            // https://api.myip.com/ (This api would return json response)
            // https://ipinfo.io/json (json only api)
            // https://ipapi.co/ip/
            // https://ifconfig.io/ip
            // https://icanhazip.com/
            // http://checkip.amazonaws.com/
            // https://api.my-ip.io/ip
            // https://api.ip.sb/ip
            
            InetAddress ipAddress = InetAddress.getByName(CrossFetch_GET("https://api.ip.sb/ip"));
            CityResponse cityResponse = reader.city(ipAddress);
            Location location = cityResponse.getLocation();
            return location;
        } catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
