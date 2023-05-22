
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
            
            // for json response use url = "https://api.ipify.org?format=json"
            InetAddress ipAddress = InetAddress.getByName(CrossFetch_GET("https://api.ipify.org"));
            CityResponse cityResponse = reader.city(ipAddress);
            
            Location location = cityResponse.getLocation();
            return location;
        } catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
